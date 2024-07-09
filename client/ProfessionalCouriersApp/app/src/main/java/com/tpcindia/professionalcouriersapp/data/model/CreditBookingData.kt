package com.tpcindia.professionalcouriersapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditBookingData(
    val currentDate: String,
    val consignmentNumber: String,
    val balanceStock: String,
    val clientName: String,
    val bookingDate: String,
    val pincode: String,
    val destination: String,
    val consigneeType: String,
    val mode: String,
    val consigneeName: String,
    val noOfPsc: String,
    val weight: String = ""
) : Parcelable
