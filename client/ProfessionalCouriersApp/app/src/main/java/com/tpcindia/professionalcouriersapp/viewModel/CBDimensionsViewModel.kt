package com.tpcindia.professionalcouriersapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CBDimensionsViewModel : ViewModel() {
    private val _selectedUnit = MutableStateFlow("")
    val selectedUnit: StateFlow<String> = _selectedUnit

    private val _length = MutableStateFlow("")
    val length: StateFlow<String> = _length

    private val _width = MutableStateFlow("")
    val width: StateFlow<String> = _width

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _lengthSum = MutableStateFlow(0)
    val lengthSum: StateFlow<Int> = _lengthSum

    private val _widthSum = MutableStateFlow(0)
    val widthSum: StateFlow<Int> = _widthSum

    private val _heightSum = MutableStateFlow(0)
    val heightSum: StateFlow<Int> = _heightSum

    fun onUnitSelected(unit: String) {
        _selectedUnit.value = unit
    }

    fun onLengthChanged(value: String, maxEntries: Int) {
        _length.value = value
        calculateSum(value, _lengthSum, maxEntries)
    }

    fun onWidthChanged(value: String, maxEntries: Int) {
        _width.value = value
        calculateSum(value, _widthSum, maxEntries)
    }

    fun onHeightChanged(value: String, maxEntries: Int) {
        _height.value = value
        calculateSum(value, _heightSum, maxEntries)
    }

    fun createCBInfoRoute(dimensionData: CBDimensionData? = null): String {
        dimensionData?.let {
            return Screen.CBInfo.createRoute(dimensionData)
        } ?: run {
            return Screen.CBInfo.createRoute(CBDimensionData(
                length = lengthSum.toString(),
                width = widthSum.toString(),
                height = heightSum.toString(),
                unit = selectedUnit.toString()
            ))
        }
    }

    fun validateEntries(length: String, width: String, height: String, maxEntries: Int): Boolean {
        val lengthCount = length.split(",").size
        val widthCount = width.split(",").size
        val heightCount = height.split(",").size
        return lengthCount == maxEntries && widthCount == maxEntries && heightCount == maxEntries
    }

    private fun calculateSum(value: String, sumState: MutableStateFlow<Int>, maxEntries: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val values = value.split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }

            if (values.size <= maxEntries) {
                sumState.value = values.sum()
            }
        }
    }
}
