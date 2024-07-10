package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.repository.CBInfoRepository
import com.tpcindia.professionalcouriersapp.viewModel.uiState.LoginState
import com.tpcindia.professionalcouriersapp.viewModel.uiState.SubmitDetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CBInfoViewModel : ViewModel() {
    private val _submitDetailsState = MutableStateFlow(SubmitDetailsState())
    val submitDetailsState: StateFlow<SubmitDetailsState> = _submitDetailsState

    private var job: Job? = null

    private val repository = CBInfoRepository(NetworkService())

    fun submitCreditBookingData(creditBookingData: CreditBookingData, cbDimensionData: CBDimensionData, cbInfoData: CBInfoData) {
        _submitDetailsState.value = SubmitDetailsState(isLoading = true)
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            if (job?.isActive == false) {
                return@launch
            }
            try {
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
            } catch (e: Exception) {
                _submitDetailsState.value = SubmitDetailsState(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun clearErrorMessage() {
        _submitDetailsState.value = _submitDetailsState.value.copy(error = null)
    }

    fun clearDataSubmissionMessage() {
        _submitDetailsState.value = _submitDetailsState.value.copy(dataSubmissionMessage = null)
    }
}