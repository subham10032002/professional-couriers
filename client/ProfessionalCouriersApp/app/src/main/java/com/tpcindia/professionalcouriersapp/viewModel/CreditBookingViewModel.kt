package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.viewModel.uiState.CreditBookingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.db.database.DatabaseProvider
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.repository.CBDataSubmissionRepository
import com.tpcindia.professionalcouriersapp.data.repository.CreditBookingRepository
import com.tpcindia.professionalcouriersapp.data.repository.LocationRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicBoolean

class CreditBookingViewModel(application: Application) : AndroidViewModel(application) {
    private val _creditBookingState = MutableStateFlow(CreditBookingState())
    val creditBookingState: StateFlow<CreditBookingState> = _creditBookingState

    private val repository: CreditBookingRepository = CreditBookingRepository(NetworkService())
    private val dataSubmissionRepository: CBDataSubmissionRepository = CBDataSubmissionRepository(NetworkService())
    private val locationRepository = LocationRepository(application)
    private val pdfDao: PdfDao = DatabaseProvider.getDatabase(application).pdfDao()
    private var job: Job? = null
    // AtomicBoolean to prevent multiple submissions in a thread-safe manner
    private val isSubmitting = AtomicBoolean(false)

    // Mutex to ensure only one thread can access the critical section at a time
    private val mutex = Mutex()

    fun fetchDestination(pincode: String) {
        if (pincode.length == 6) {
            _creditBookingState.value = _creditBookingState.value.copy(
                isLoading = true,
            )
            job?.cancel()
            if (job?.isActive == true) {
                return
            }
            job = viewModelScope.launch(Dispatchers.Main) {
                try {
                    val result = repository.getDestination(pincode)
                    if (result.isSuccess) {
                        _creditBookingState.value = _creditBookingState.value.copy(
                            isLoading = false, destinationOptions = result.getOrThrow()
                        )
                    } else {
                        updateStateWithError("Failed to fetch destination")
                    }
                } catch (e: Exception) {
                    updateStateWithError("Failed to fetch destination: ${e.message}")
                }
            }
        }
    }

    private fun submitCreditBookingData(creditBookingData: CreditBookingData, cbDimensionData: CBDimensionData = CBDimensionData(), cbInfoData: CBInfoData = CBInfoData()) {
        job?.cancel()
        if (job?.isActive == true) {
            return
        }
        job = viewModelScope.launch(Dispatchers.Main) {
            // Introduce delay for debounce
            delay(500)

            // Check if already submitting using AtomicBoolean
            if (isSubmitting.get()) {
                _creditBookingState.value = _creditBookingState.value.copy(
                    error = "Submission is already in progress. Please wait."
                )
                return@launch
            }

            // Set the flag to true indicating submission is in progress
            if (isSubmitting.compareAndSet(false, true)) {
                try {
                    mutex.withLock {
                        _creditBookingState.value = _creditBookingState.value.copy(
                            isLoading = true,
                        )
                        val currentLocation = async { locationRepository.getLocation() }

                        // Launch network calls concurrently
                        val masterAddressDeferred =
                            async {
                                dataSubmissionRepository.getMasterAddressDetails(
                                    creditBookingData.masterCompanyCode
                                )
                            }
                        val consignmentDeferred = async {
                            dataSubmissionRepository.getConsignmentDetails(
                                creditBookingData.branch,
                                creditBookingData.userCode
                            )
                        }

                        // Await both results
                        val masterAddressResult = masterAddressDeferred.await()
                        val consignmentResult = consignmentDeferred.await()
                        val locationData = currentLocation.await()
                        ensureActive()
                        if (consignmentResult.isSuccess && masterAddressResult.isSuccess) {

                            creditBookingData.longitude = locationData.longitude.toString()
                            creditBookingData.latitude = locationData.latitude.toString()

                            val balanceStock = consignmentResult.getOrThrow().balanceStock
                            val consignmentNumber =
                                consignmentResult.getOrThrow().accCode + consignmentResult.getOrThrow().consignmentNo
                            val masterAddressDetails = masterAddressResult.getOrThrow()

                            creditBookingData.balanceStock = balanceStock
                            creditBookingData.consignmentNumber = consignmentNumber
                            creditBookingData.masterAddressDetails = masterAddressDetails

                            val pdfAddress = dataSubmissionRepository.createPdf(
                                getApplication(),
                                creditBookingData,
                                cbDimensionData,
                                cbInfoData
                            )
                            creditBookingData.pdfAddress = pdfAddress

                            _creditBookingState.value = _creditBookingState.value.copy(
                                consignmentNumber = consignmentNumber,
                                balanceStock = balanceStock,
                                masterAddressDetails = masterAddressDetails,
                                pdfAddress = pdfAddress
                            )
                            val result = dataSubmissionRepository.submitCreditBookingDetails(
                                creditBookingData = creditBookingData,
                                cbDimensionData = cbDimensionData,
                                cbInfoData = cbInfoData
                            )
                            if (result.isSuccess) {
                                _creditBookingState.value = _creditBookingState.value.copy(
                                    isDataSubmitted = true
                                )
                            } else {
                                updateStateWithError(
                                    result.exceptionOrNull()?.message
                                        ?: "Failed to submit credit booking data"
                                )
                            }
                        } else {
                            updateStateWithError(
                                consignmentResult.exceptionOrNull()?.message
                                    ?: "Failed to fetch consignment details"
                            )
                        }
                    }
                } catch (e: Exception) {
                    updateStateWithError("Failed to submit credit booking data: ${e.message}")
                    isSubmitting.set(false)
                }
            }
        }
    }

    fun onButtonClicked(creditBookingData: CreditBookingData) {
        if (!_creditBookingState.value.isLoading && !isSubmitting.get()) {
            submitCreditBookingData(
                creditBookingData = creditBookingData,
            )
        } else {
            _creditBookingState.value = _creditBookingState.value.copy(error = "Please wait we're submitting the data")
        }
    }

    private fun updateStateWithError(errorMsg: String) {
        _creditBookingState.value = _creditBookingState.value.copy(
            isLoading = false,
            error = errorMsg,
            pdfAddress = null
        )
    }

    fun createPDFScreenRoute(uniqueUser: String): String {
        return Screen.PdfScreen.createRoute(uniqueUser)
    }

    fun savePdf(pdfData: ByteArray, fileName: String, uniqueUser: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _creditBookingState.value = _creditBookingState.value.copy(
                isDataSubmitted = false,
                isPdfSaved = false,
            )
            try {
                val isSaved = dataSubmissionRepository.savePdf(pdfData, fileName, uniqueUser, pdfDao)
                if (isSaved) {
                    _creditBookingState.value = _creditBookingState.value.copy(
                        isLoading = false,
                        isPdfSaved = true,
                    )
                } else {
                    _creditBookingState.value = _creditBookingState.value.copy(
                        isLoading = false,
                        isPdfSaved = false,
                    )
                    Toast.makeText(getApplication(), "Failed to save PDF", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                _creditBookingState.value = _creditBookingState.value.copy(
                    isLoading = false,
                    isPdfSaved = false,
                )
                isSubmitting.set(false)
                Toast.makeText(getApplication(), "Failed to save PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onWeightChanged(weight: String) {
        _creditBookingState.value = _creditBookingState.value.copy(weight = weight)
        val weightValue = weight.toFloatOrNull()
        val submitEnabled = weightValue?.let { it <= 1 } ?: false
        _creditBookingState.value = _creditBookingState.value.copy(submitEnabled = submitEnabled)
    }

    fun createCBDimensionRoute(): String {
        return Screen.CBDimensions.route
    }

    fun setLoading(isLoading: Boolean) {
        _creditBookingState.value = _creditBookingState.value.copy(isLoading = isLoading)
    }

    fun clearErrorMessage() {
        _creditBookingState.value = _creditBookingState.value.copy(error = null)
    }

    fun clearState() {
        _creditBookingState.value = CreditBookingState()
        isSubmitting.set(false)
    }

}