package com.tpcindia.professionalcouriersapp.viewModel.uiState

import com.tpcindia.professionalcouriersapp.data.model.response.DestinationDetails

data class CreditBookingState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val destinationOptions: List<DestinationDetails> = emptyList()
)