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
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.viewModel.uiState.CreditBookingState
import com.tpcindia.professionalcouriersapp.viewModel.uiState.SubmitDetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CBInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val _submitDetailsState = MutableStateFlow(SubmitDetailsState())
    val submitDetailsState: StateFlow<SubmitDetailsState> = _submitDetailsState

    private var job: Job? = null

    private val repository = CBDataSubmissionRepository(NetworkService())
    private val pdfDao: PdfDao = DatabaseProvider.getDatabase(application).pdfDao()

    fun submitCreditBookingData(creditBookingData: CreditBookingData, cbDimensionData: CBDimensionData, cbInfoData: CBInfoData) {
        _submitDetailsState.value = SubmitDetailsState(isLoading = true)
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            if (job?.isActive == false) {
                return@launch
            }
            try {
                val consignmentDetails = repository.getConsignmentDetails(creditBookingData.branch)
                if (consignmentDetails.isSuccess) {
                    val result = repository.submitCreditBookingDetails(
                        creditBookingData = creditBookingData,
                        cbDimensionData = cbDimensionData,
                        cbInfoData = cbInfoData
                    )
                    if (result.isSuccess) {
                        _submitDetailsState.value = SubmitDetailsState(
                            isLoading = false,
                            isDataSubmitted = true,
                            dataSubmissionMessage = result.getOrThrow()
                        )
                    } else {
                        _submitDetailsState.value = SubmitDetailsState(
                            error = result.exceptionOrNull()?.message,
                            isLoading = false
                        )
                    }
                } else {
                    _submitDetailsState.value = SubmitDetailsState(
                        error = consignmentDetails.exceptionOrNull()?.message,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _submitDetailsState.value = SubmitDetailsState(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun createPdf(context: Context, creditBookingData: CreditBookingData,
                  cbDimensionData: CBDimensionData,
                  cbInfoData: CBInfoData) : ByteArray {
        return repository.createPdf(context, creditBookingData, cbDimensionData, cbInfoData)
    }

    fun savePdf(pdfData: ByteArray, fileName: String, branch: String) {
        _submitDetailsState.value = SubmitDetailsState(isLoading = true, isPdfSaved = false)
        viewModelScope.launch {
            val isSaved = repository.savePdf(pdfData, fileName, branch, pdfDao)
            if (isSaved) {
                _submitDetailsState.value = SubmitDetailsState(isLoading = false, isPdfSaved = true)
            } else {
                _submitDetailsState.value = SubmitDetailsState(isLoading = false, isPdfSaved = false)
                Toast.makeText(getApplication(), "Failed to save PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createPDFScreenRoute(branch: String): String {
        return Screen.PdfScreen.createRoute(branch)
    }

    fun clearErrorMessage() {
        _submitDetailsState.value = _submitDetailsState.value.copy(error = null)
    }

    fun clearDataSubmissionMessage() {
        _submitDetailsState.value = _submitDetailsState.value.copy(dataSubmissionMessage = null)
    }

    fun clearState() {
        _submitDetailsState.value = SubmitDetailsState()
    }
}