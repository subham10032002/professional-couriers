package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val firmNames: List<String> = emptyList(),
    val isDataFetched: Boolean = false,
    val isEmailSending: Boolean = false,
    val emailSentSuccessfully: Boolean = false,
    val emailSendingFailed: Boolean = false,
    val isBookingCardClicked: Boolean = false
)