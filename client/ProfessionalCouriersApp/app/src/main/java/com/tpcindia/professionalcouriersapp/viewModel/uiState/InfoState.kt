package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class InfoState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDataSubmitted: Boolean = false,
    val isPdfSaved: Boolean = false,
    val invoiceValue: String = "",
    val invoiceNumber: String = "",
    val product: String = "",
    val ewaybill: String = "",
    val pdfAddress: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InfoState

        if (isLoading != other.isLoading) return false
        if (error != other.error) return false
        if (isDataSubmitted != other.isDataSubmitted) return false
        if (isPdfSaved != other.isPdfSaved) return false
        if (invoiceValue != other.invoiceValue) return false
        if (invoiceNumber != other.invoiceNumber) return false
        if (product != other.product) return false
        if (ewaybill != other.ewaybill) return false
        if (pdfAddress != null) {
            if (other.pdfAddress == null) return false
            if (!pdfAddress.contentEquals(other.pdfAddress)) return false
        } else if (other.pdfAddress != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + isDataSubmitted.hashCode()
        result = 31 * result + isPdfSaved.hashCode()
        result = 31 * result + invoiceValue.hashCode()
        result = 31 * result + invoiceNumber.hashCode()
        result = 31 * result + product.hashCode()
        result = 31 * result + ewaybill.hashCode()
        result = 31 * result + (pdfAddress?.contentHashCode() ?: 0)
        return result
    }
}