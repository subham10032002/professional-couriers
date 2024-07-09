package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.viewModel.uiState.CreditBookingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.repository.CreditBookingRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreditBookingViewModel : ViewModel() {
    private val _creditBookingState = MutableStateFlow(CreditBookingState())
    val creditBookingState: StateFlow<CreditBookingState> = _creditBookingState

    private val repository: CreditBookingRepository = CreditBookingRepository(NetworkService())

    fun fetchDestination(pincode: String) {
        if (pincode.length == 6) {
            _creditBookingState.value = CreditBookingState(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = repository.getDestination(pincode)
                    if (result.isSuccess) {
                        _creditBookingState.value = CreditBookingState(isLoading = false, destinationOptions = result.getOrThrow())
                    } else {
                        updateStateWithError("Failed to fetch destination")
                    }
                } catch (e: Exception) {
                    updateStateWithError("Failed to fetch destination: ${e.message}")
                }
            }
        }
    }

    private fun updateStateWithError(errorMsg: String) {
        _creditBookingState.value = CreditBookingState(
            isLoading = false,
            error = errorMsg,
        )
    }

    fun createCBDimensionRoute(bookingData: CreditBookingData): String {
        return Screen.CBDimensions.createRoute(bookingData)
    }

    fun clearErrorMessage() {
        _creditBookingState.value = _creditBookingState.value.copy(error = null)
    }

}