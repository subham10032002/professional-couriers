package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.repository.HomeRepository
import com.tpcindia.professionalcouriersapp.viewModel.uiState.HomeState
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.data.model.response.ConsignmentDetails
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

    fun onBookingClick(branchCode: String) {
        _homeState.value = HomeState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val firmNameResult = fetchFirmNames(branchCode)
                if (firmNameResult.isSuccess) {
                    val firmNames = firmNameResult.getOrThrow()
                    if (firmNames.isNotEmpty()) {
                        val firmName = firmNames.first().firmName
                        fetchConsignmentDetails(firmName)
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

    private fun fetchFirmNames(branchCode: String): Result<List<ClientDetails>> {
        return repository.getFirmNames(branchCode)
    }

    private fun fetchConsignmentDetails(firmName: String) {
        val consignmentDetailsResult = repository.getConsignmentDetails(firmName)
        if (consignmentDetailsResult.isSuccess) {
            val consignmentDetails = consignmentDetailsResult.getOrThrow()
            updateStateWithDetails(consignmentDetails, firmName)
        } else {
            updateStateWithError("Failed to fetch consignment details")
        }
    }

    private fun updateStateWithDetails(consignmentDetails: ConsignmentDetails, firmName: String) {
        _homeState.value = HomeState(
            isLoading = false,
            isDataFetched = true,
            consignmentNumber = consignmentDetails.consignmentNo,
            balanceStock = consignmentDetails.balanceStock,
            firmName = firmName
        )
    }

    private fun updateStateWithError(errorMsg: String) {
        _homeState.value = HomeState(
            isLoading = false,
            error = errorMsg,
            isDataFetched = false,
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
    fun createLoginScreenRoute(): String? {
        if (_homeState.value.firmName == null ||
            _homeState.value.consignmentNumber == null ||
            _homeState.value.balanceStock == null ) return null
        val currentDate = getCurrentData()
        val (day, month, year) = currentDate.split("/")

        return Screen.CreditBooking.createRoute(
            firmName = _homeState.value.firmName!!,
            balanceStock = _homeState.value.balanceStock!!,
            consignmentNumber = _homeState.value.consignmentNumber!!,
            day = day,
            month = month,
            year = year
        )

    }

}