package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.model.HomeScreenData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _creditBookingData = MutableStateFlow(CreditBookingData())
    val creditBookingData: StateFlow<CreditBookingData> = _creditBookingData

    private val _cbDimensionData = MutableStateFlow(CBDimensionData())
    val cbDimensionData: StateFlow<CBDimensionData> = _cbDimensionData

    private val _homeScreenData = MutableStateFlow(HomeScreenData())
    val homeScreenData: StateFlow<HomeScreenData> = _homeScreenData

    fun setCreditBookingData(data: CreditBookingData) {
        _creditBookingData.value = data
    }

    fun setCBDimesionData(data: CBDimensionData) {
        _cbDimensionData.value = data
    }

    fun setHomeScreenData(data: HomeScreenData) {
        _homeScreenData.value = data
    }

    fun clearState() {
        _creditBookingData.value = CreditBookingData()
        _cbDimensionData.value = CBDimensionData()
        _homeScreenData.value = HomeScreenData()
    }
}