package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _creditBookingData = MutableStateFlow(CreditBookingData())
    val creditBookingData: StateFlow<CreditBookingData> = _creditBookingData

    private val _cbDimensionData = MutableStateFlow(CBDimensionData())
    val cbDimensionData: StateFlow<CBDimensionData> = _cbDimensionData

    fun setCreditBookingData(data: CreditBookingData) {
        _creditBookingData.value = data
    }

    fun setCBDimesionData(data: CBDimensionData) {
        _cbDimensionData.value = data
    }
}