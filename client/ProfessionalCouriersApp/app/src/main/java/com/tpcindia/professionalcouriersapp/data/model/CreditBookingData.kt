package com.tpcindia.professionalcouriersapp.data.model

import android.os.Parcelable
import com.tpcindia.professionalcouriersapp.data.model.response.DestinationDetails
import com.tpcindia.professionalcouriersapp.data.model.response.MasterAddressDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditBookingData(
    val username: String = "",
    val userCode: String = "",
    val currentDate: String = "",
    var consignmentNumber: String = "",
    var balanceStock: String = "",
    val branch: String = "branch",
    val clientName: String = "",
    val clientAddress: String = "",
    val clientContact: String = "",
    val masterCompanyCode: String = "",
    val bookingDate: String = "",
    val pincode: String = "",
    val destination: String = "",
    val destDetails: DestinationDetails = DestinationDetails(),
    var masterAddressDetails: MasterAddressDetails = MasterAddressDetails(),
    val consigneeType: String = "",
    val mode: String = "",
    val consigneeName: String = "",
    val noOfPsc: String = "",
    val weight: String = "",
    var longitude: String = "",
    var latitude: String = "",
    val transactionId: String = "",
    val photoOfAddress: ByteArray? = null,
    var pdfAddress: ByteArray? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreditBookingData

        if (username != other.username) return false
        if (userCode != other.userCode) return false
        if (currentDate != other.currentDate) return false
        if (consignmentNumber != other.consignmentNumber) return false
        if (balanceStock != other.balanceStock) return false
        if (branch != other.branch) return false
        if (clientName != other.clientName) return false
        if (bookingDate != other.bookingDate) return false
        if (pincode != other.pincode) return false
        if (destination != other.destination) return false
        if (consigneeType != other.consigneeType) return false
        if (mode != other.mode) return false
        if (consigneeName != other.consigneeName) return false
        if (noOfPsc != other.noOfPsc) return false
        if (weight != other.weight) return false
        if (longitude != other.longitude) return false
        if (latitude != other.latitude) return false
        if (photoOfAddress != null) {
            if (other.photoOfAddress == null) return false
            if (!photoOfAddress.contentEquals(other.photoOfAddress)) return false
        } else if (other.photoOfAddress != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + userCode.hashCode()
        result = 31 * result + currentDate.hashCode()
        result = 31 * result + consignmentNumber.hashCode()
        result = 31 * result + balanceStock.hashCode()
        result = 31 * result + branch.hashCode()
        result = 31 * result + clientName.hashCode()
        result = 31 * result + bookingDate.hashCode()
        result = 31 * result + pincode.hashCode()
        result = 31 * result + destination.hashCode()
        result = 31 * result + consigneeType.hashCode()
        result = 31 * result + mode.hashCode()
        result = 31 * result + consigneeName.hashCode()
        result = 31 * result + noOfPsc.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + (photoOfAddress?.contentHashCode() ?: 0)
        return result
    }
}