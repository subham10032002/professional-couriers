package com.tpcindia.professionalcouriersapp.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DestinationDetails(
    val city: String = "",
    val destCode: String? = "",
    val destn : String? = "",
    val areaCode: String? = "",
    val hub: String? = "",
    val state: String? = "",
) : Parcelable