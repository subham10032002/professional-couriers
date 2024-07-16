package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.repository.HomeRepository
import com.tpcindia.professionalcouriersapp.viewModel.uiState.HomeState
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    private val repository: HomeRepository = HomeRepository(NetworkService())

    fun onBookingClick(branch: String) {
        _homeState.value = HomeState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val firmNameResult = fetchFirmNames(branch)
                if (firmNameResult.isSuccess) {
                    val firmNames = firmNameResult.getOrThrow()
                    if (firmNames.isNotEmpty()) {
                        updateStateWithDetails(firmNames = firmNames.map {
                            it.firmName
                        })
                    } else {
                        updateStateWithError("No firm names found")
                    }
                } else {
                    updateStateWithError("Failed to fetch firm names")
                }
            } catch (e: Exception) {
                updateStateWithError("Failed to fetch details: ${e.message}")
            }
        }
    }

    fun fetchCreditBookingDetails(branch: String) {
        _homeState.value = HomeState(isEmailSending = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {

            } catch (e: Exception) {
                updateStateWithError("Failed to fetch details: ${e.message}")
            }
        }
    }

    private fun fetchFirmNames(branch: String): Result<List<ClientDetails>> {
        return repository.getFirmNames(branch)
    }

    private fun updateStateWithDetails(firmNames: List<String>) {
        _homeState.value = HomeState(
            isLoading = false,
            isDataFetched = true,
            firmNames = firmNames
        )
    }

    private fun updateStateWithError(errorMsg: String) {
        _homeState.value = HomeState(
            isLoading = false,
            error = errorMsg,
            isDataFetched = false,
            isEmailSending = false
        )
    }

    private fun getCurrentData(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun clearErrorMessage() {
        _homeState.value = _homeState.value.copy(error = null)
    }

    // Function to create navigation route to CreditBooking
    fun createCreditBookingScreenRoute(branch: String): String {
        val currentDate = getCurrentData()
        val (day, month, year) = currentDate.split("/")

        return Screen.CreditBooking.createRoute(
            firmNames = _homeState.value.firmNames,
            day = day,
            month = month,
            year = year,
            branch = branch
        )
    }

    fun createPDFScreenRoute(branch: String): String {
        return Screen.PdfScreen.createRoute(branch)
    }

    fun clearState() {
        _homeState.value = HomeState()
    }

}