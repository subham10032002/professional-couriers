package com.tpcindia.professionalcouriersapp.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class  MasterAddressDetails(
    val code: String? = "",
    val address: String? = "",
    val contactNo: String? = "",
    val gstNo: String? = "",
    val subBranchCode: String? = ""
) : Parcelable