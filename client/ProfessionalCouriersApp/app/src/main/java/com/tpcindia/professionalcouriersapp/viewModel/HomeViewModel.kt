package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.repository.HomeRepository
import com.tpcindia.professionalcouriersapp.viewModel.uiState.HomeState
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.HomeScreenData
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.data.repository.LoginRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val clientDetailsResult = fetchClientDetails(branch)
                if (clientDetailsResult.isSuccess) {
                    val clientDetails = clientDetailsResult.getOrThrow()
                    if (clientDetails.isNotEmpty()) {
                        updateStateWithDetails(clientDetails = clientDetails)
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

    fun sendEmails(branch: String, branchCode: String, userName: String, usercode: String) {
        if (job?.isActive == true) {
            job?.cancel()
            updateEmailStateFailure(errorMsg = "Email sending in progress stopped")
            return
        }
        job?.cancel()
        _homeState.value = HomeState(
            isEmailSending = true
        )
        job = viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = repository.sendEmails(
                    branch = branch,
                    branchCode = branchCode,
                    userName = userName,
                    usercode = usercode
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
        viewModelScope.launch(Dispatchers.Main) {
            loginRepository.clearUser(context = getApplication())
        }
    }

    private suspend fun fetchClientDetails(branch: String): Result<List<ClientDetails>> {
        return repository.getClientDetails(branch)
    }

    private fun updateStateWithDetails(clientDetails: List<ClientDetails>) {
        _homeState.value = HomeState(
            isLoading = false,
            isDataFetched = true,
            clientDetails = clientDetails
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
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun clearErrorMessage() {
        _homeState.value = _homeState.value.copy(error = null)
    }

    // Function to create navigation route to CreditBooking
    fun createCreditBookingScreenRoute(
        branch: String,
        userName: String,
        userCode: String,
        sharedViewModel: SharedViewModel
    ): String {
        val currentDate = getCurrentData()

        sharedViewModel.setHomeScreenData(
            HomeScreenData(
                clientDetails = _homeState.value.clientDetails,
                currentDate = currentDate,
                branch = branch,
                username = userName,
                userCode = userCode
            )
        )
        return Screen.CreditBooking.route
    }

    fun getLoginScreenRoute() : String {
        return Screen.Login.route
    }

    fun getHomeScreenRoute() : String {
        return Screen.Home.route
    }

    fun createPDFScreenRoute(uniqueUser: String, branch: String, userCode: String): String {
        return Screen.PdfScreen.createRoute(
            uniqueUser = uniqueUser,
            branch = branch,
            userCode = userCode,)
    }

    fun clearBookingCardClicked() {
        _homeState.value = _homeState.value.copy(isBookingCardClicked = false)
    }

    fun clearState() {
        _homeState.value = HomeState()
    }

}