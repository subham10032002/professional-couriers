package com.tpcindia.professionalcouriersapp.viewModel.uiState

import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val clientDetails: List<ClientDetails> = emptyList(),
    val isDataFetched: Boolean = false,
    val isEmailSending: Boolean = false,
    val emailSentSuccessfully: Boolean = false,
    val emailSendingFailed: Boolean = false,
    val isBookingCardClicked: Boolean = false
)