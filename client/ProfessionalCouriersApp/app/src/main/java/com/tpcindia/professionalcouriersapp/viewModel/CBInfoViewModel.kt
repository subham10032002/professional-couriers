package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.db.database.DatabaseProvider
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.repository.CBDataSubmissionRepository
import com.tpcindia.professionalcouriersapp.data.repository.LocationRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.viewModel.uiState.InfoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CBInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val _infoState = MutableStateFlow(InfoState())
    val infoState: StateFlow<InfoState> = _infoState

    private var job: Job? = null

    private val repository = CBDataSubmissionRepository(NetworkService())
    private val locationRepository = LocationRepository(application)
    private val pdfDao: PdfDao = DatabaseProvider.getDatabase(application).pdfDao()

    private fun submitCreditBookingData(
        creditBookingData: CreditBookingData,
        cbDimensionData: CBDimensionData
    ) {
        _infoState.value =  _infoState.value.copy(isLoading = true)
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            if (job?.isActive == false) {
                return@launch
            }
            try {
                val currentLocation = locationRepository.getLocation()
                creditBookingData.longitude = currentLocation.longitude.toString()
                creditBookingData.latitude = currentLocation.latitude.toString()

                // Launch network calls concurrently
                val masterAddressDeferred = async { repository.getMasterAddressDetails(creditBookingData.masterCompanyCode) }
                val consignmentDeferred = async { repository.getConsignmentDetails(creditBookingData.branch, creditBookingData.userCode) }

                // Await both results
                val masterAddressResult = masterAddressDeferred.await()
                val consignmentResult = consignmentDeferred.await()

                if (consignmentResult.isSuccess && consignmentResult.isSuccess) {
                    creditBookingData.balanceStock = consignmentResult.getOrThrow().balanceStock
                    creditBookingData.consignmentNumber = consignmentResult.getOrThrow().accCode +
                            consignmentResult.getOrThrow().consignmentNo
                    creditBookingData.masterAddressDetails = masterAddressResult.getOrThrow()

                    val pdfAddress = repository.createPdf(getApplication(), creditBookingData, cbDimensionData, getCreditInfoData())
                    creditBookingData.pdfAddress = pdfAddress

                    val result = repository.submitCreditBookingDetails(
                        creditBookingData = creditBookingData,
                        cbDimensionData = cbDimensionData,
                        cbInfoData = getCreditInfoData()
                    )
                    if (result.isSuccess) {
                        _infoState.value =  _infoState.value.copy(
                            isLoading = false,
                            isDataSubmitted = true,
                            pdfAddress = pdfAddress
                        )
                    } else {
                        updateStateWithError(result.exceptionOrNull()?.message ?: "Failed to submit credit booking data")
                    }
                } else {
                    updateStateWithError(consignmentResult.exceptionOrNull()?.message ?: "Failed to fetch consignment details")
                }
            } catch (e: Exception) {
                updateStateWithError("Failed to submit credit booking data: ${e.message}")
            }
        }
    }

    private fun updateStateWithError(errorMsg: String) {
        _infoState.value = _infoState.value.copy(
            error = errorMsg,
            isLoading = false,
            pdfAddress = null
        )
    }

    fun savePdf(pdfData: ByteArray, fileName: String, branch: String) {
        _infoState.value =  _infoState.value.copy(isLoading = true, isPdfSaved = false)
        viewModelScope.launch {
            val isSaved = repository.savePdf(pdfData, fileName, branch, pdfDao)
            if (isSaved) {
                _infoState.value =  _infoState.value.copy(isLoading = false, isPdfSaved = true)
            } else {
                _infoState.value =  _infoState.value.copy(isLoading = false, isPdfSaved = false)
                Toast.makeText(getApplication(), "Failed to save PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onButtonClicked(creditBookingData: CreditBookingData, cbDimensionData: CBDimensionData) {
        if (!_infoState.value.isLoading) {
            val updateInvoiceValue = _infoState.value.invoiceValue.toIntOrNull()
            if (updateInvoiceValue != null && updateInvoiceValue >= 50000 && isMandatoryFieldBlank()) {
                _infoState.value = _infoState.value.copy(error = "Please fill the mandatory fields")
                return
            }
            submitCreditBookingData(
                creditBookingData = creditBookingData,
                cbDimensionData = cbDimensionData
            )
        } else {
            _infoState.value = _infoState.value.copy(error = "Please wait we're submitting the data")
        }
    }

    private fun isMandatoryFieldBlank() : Boolean {
        return when {
            _infoState.value.product.isBlank() -> true
            _infoState.value.ewaybill.isBlank() -> true
            _infoState.value.invoiceValue.isBlank() -> true
            _infoState.value.invoiceNumber.isBlank() -> true
            else -> false
        }
    }

    private fun getCreditInfoData() : CBInfoData {
        return CBInfoData(
            invoiceNumber = _infoState.value.invoiceNumber,
            product = _infoState.value.product,
            invoiceValue = _infoState.value.invoiceValue,
            ewayBill = _infoState.value.ewaybill
        )
    }

    fun setProductValue(value: String) {
        _infoState.value = _infoState.value.copy(product = value)
    }

    fun setEwaybillValue(value: String) {
        _infoState.value = _infoState.value.copy(ewaybill = value)
    }

    fun setInvoiceValue(value: String) {
        _infoState.value = _infoState.value.copy(invoiceValue = value)
    }

    fun setInvoiceNumberValue(value: String) {
        _infoState.value = _infoState.value.copy(invoiceNumber = value)
    }

    fun createPDFScreenRoute(branch: String): String {
        return Screen.PdfScreen.createRoute(branch)
    }

    fun clearErrorMessage() {
        _infoState.value = _infoState.value.copy(error = null)
    }

    fun clearDataSubmitted() {
        _infoState.value = _infoState.value.copy(isDataSubmitted = false)
    }

    fun clearPDFSavedState() {
        _infoState.value = _infoState.value.copy(isPdfSaved = false)
    }

    fun clearState() {
        _infoState.value = InfoState()
    }
}