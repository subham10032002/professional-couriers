package com.tpcindia.professionalcouriersapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CBDimensionData(
    val unit: String = "",
    val length: String = "",
    val width: String = "",
    val height: String = "",
) : Parcelable
