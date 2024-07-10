package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class SubmitDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDataSubmitted: Boolean = false,
    val dataSubmissionMessage: String? = null
)