package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val consignmentNumber: String? = null,
    val firmName: String? = null,
    val balanceStock: String? = null,
    val isDataFetched: Boolean = false
)