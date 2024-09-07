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
    val pdfAddress: ByteArray? = null,
    val masterAddressDetails: MasterAddressDetails = MasterAddressDetails()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreditBookingState

        if (error != other.error) return false
        if (isLoading != other.isLoading) return false
        if (destinationOptions != other.destinationOptions) return false
        if (weight != other.weight) return false
        if (submitEnabled != other.submitEnabled) return false
        if (isPdfSaved != other.isPdfSaved) return false
        if (isDataSubmitted != other.isDataSubmitted) return false
        if (consignmentNumber != other.consignmentNumber) return false
        if (balanceStock != other.balanceStock) return false
        if (pdfAddress != null) {
            if (other.pdfAddress == null) return false
            if (!pdfAddress.contentEquals(other.pdfAddress)) return false
        } else if (other.pdfAddress != null) return false
        if (masterAddressDetails != other.masterAddressDetails) return false

        return true
    }

    override fun hashCode(): Int {
        var result = error?.hashCode() ?: 0
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + destinationOptions.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + submitEnabled.hashCode()
        result = 31 * result + isPdfSaved.hashCode()
        result = 31 * result + isDataSubmitted.hashCode()
        result = 31 * result + consignmentNumber.hashCode()
        result = 31 * result + balanceStock.hashCode()
        result = 31 * result + (pdfAddress?.contentHashCode() ?: 0)
        result = 31 * result + masterAddressDetails.hashCode()
        return result
    }
}