package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.repository.HomeRepository
import com.tpcindia.professionalcouriersapp.viewModel.uiState.HomeState
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.data.repository.LoginRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    private val repository: HomeRepository = HomeRepository(NetworkService())
    private val loginRepository: LoginRepository = LoginRepository(NetworkService())
    private var job : Job? = null

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

    fun onBookingCardClicked() {
        _homeState.value = _homeState.value.copy(isBookingCardClicked = true)
    }

    fun sendEmails(branch: String, branchCode: String, userName: String) {
        if (job?.isActive == true) {
            job?.cancel()
            updateEmailStateFailure(errorMsg = "Email sending in progress stopped")
            return
        }
        job?.cancel()
        _homeState.value = HomeState(
            isEmailSending = true
        )
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) {
                    return@launch
                }
                val result = repository.sendEmails(
                    branch = branch,
                    branchCode = branchCode,
                    userName = userName
                )
                if (result.isSuccess) {
                    _homeState.value = HomeState(
                        emailSentSuccessfully = true
                    )
                } else {
                    updateEmailStateFailure(result.exceptionOrNull()?.message ?: "Failed to send emails")
                }
            } catch(e: Exception) {
                updateEmailStateFailure("Failed to send emails: ${e.message}")
            }
        }
    }

    fun logout() {
        loginRepository.clearUser(context = getApplication())
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

    private fun updateEmailStateFailure(errorMsg: String) {
        _homeState.value = HomeState(
            error = errorMsg,
            emailSendingFailed = true,
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
    fun createCreditBookingScreenRoute(branch: String, userName: String, userCode: String): String {
        val currentDate = getCurrentData()
        val (day, month, year) = currentDate.split("/")

        return Screen.CreditBooking.createRoute(
            firmNames = _homeState.value.firmNames,
            day = day,
            month = month,
            year = year,
            branch = branch,
            username = userName,
            userCode = userCode
        )
    }

    fun getLoginScreenRoute() : String {
        return Screen.Login.route
    }

    fun getHomeScreenRoute() : String {
        return Screen.Home.route
    }

    fun createPDFScreenRoute(branch: String): String {
        return Screen.PdfScreen.createRoute(branch)
    }

    fun clearBookingCardClicked() {
        _homeState.value = _homeState.value.copy(isBookingCardClicked = false)
    }

    fun clearState() {
        _homeState.value = HomeState()
    }

}