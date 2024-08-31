package com.tpcindia.professionalcouriersapp.viewModel.uiState

import com.tpcindia.professionalcouriersapp.data.model.response.DestinationDetails
import com.tpcindia.professionalcouriersapp.data.model.response.MasterAddressDetails

data class CreditBookingState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val destinationOptions: List<DestinationDetails> = emptyList(),
    val weight: String = "",
    val submitEnabled: Boolean = false,
    val isPdfSaved: Boolean = false,
    val isDataSubmitted: Boolean = false,
    val consignmentNumber: String = "",
    val balanceStock: String = "",
    val masterAddressDetails: MasterAddressDetails = MasterAddressDetails()
)