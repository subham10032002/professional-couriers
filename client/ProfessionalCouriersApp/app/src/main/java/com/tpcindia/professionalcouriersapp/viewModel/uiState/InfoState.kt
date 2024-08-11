package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class InfoState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDataSubmitted: Boolean = false,
    val isPdfSaved: Boolean = false,
    val invoiceValue: String = "",
    val invoiceNumber: String = "",
    val product: String = "",
    val ewaybill: String = ""
)