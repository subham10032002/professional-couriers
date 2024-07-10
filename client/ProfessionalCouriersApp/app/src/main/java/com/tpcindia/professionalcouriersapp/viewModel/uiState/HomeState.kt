package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val consignmentNumber: String = "",
    val firmName: String = "",
    val balanceStock: String = "",
    val isDataFetched: Boolean = false
)