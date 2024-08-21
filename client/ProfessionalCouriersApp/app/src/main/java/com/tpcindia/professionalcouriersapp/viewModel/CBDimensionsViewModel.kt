package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CBDimensionsViewModel(application: Application) : AndroidViewModel(application) {
    private val _selectedUnit = MutableStateFlow("KG")
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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

    fun createCBInfoRoute(sharedViewModel: SharedViewModel): String {
        sharedViewModel.setCBDimesionData(
            CBDimensionData(
                length = lengthSum.value.toString(),
                width = widthSum.value.toString(),
                height = heightSum.value.toString(),
                unit = selectedUnit.value
        )
        )
        return Screen.CBInfo.route
    }

    fun validateEntries(length: String, width: String, height: String, maxEntries: Int): Boolean {
        val lengthCount = if (length.isEmpty()) 0 else length.split(",").size
        val widthCount = if (width.isEmpty()) 0 else width.split(",").size
        val heightCount = if (height.isEmpty()) 0 else height.split(",").size
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

    fun clearErrorMessage() {
        _error.value = null
    }

    fun clearState() {
        _selectedUnit.value = ""
        _length.value = ""
        _width.value = ""
        _height.value = ""
        _lengthSum.value = 0
        _widthSum.value = 0
        _isLoading.value = false
    }
}
