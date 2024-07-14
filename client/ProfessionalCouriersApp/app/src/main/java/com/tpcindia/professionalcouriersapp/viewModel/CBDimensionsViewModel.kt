package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.db.database.DatabaseProvider
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.repository.CBDataSubmissionRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CBDimensionsViewModel(application: Application) : AndroidViewModel(application) {
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isDataSubmitted = MutableStateFlow(false)
    val isDataSubmitted: StateFlow<Boolean> = _isDataSubmitted

    private val _isPdfSaved = MutableStateFlow(false)
    val isPdfSaved: StateFlow<Boolean> = _isPdfSaved

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var job: Job? = null

    private val repository = CBDataSubmissionRepository(NetworkService())
    private val pdfDao: PdfDao = DatabaseProvider.getDatabase(application).pdfDao()

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

    fun createCBInfoRoute(dimensionData: CBDimensionData? = null, bookingData: CreditBookingData): String {
        dimensionData?.let {
            return Screen.CBInfo.createRoute(dimensionData, bookingData)
        } ?: run {
            return Screen.CBInfo.createRoute(CBDimensionData(
                length = lengthSum.toString(),
                width = widthSum.toString(),
                height = heightSum.toString(),
                unit = selectedUnit.toString()
            ), bookingData)
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

    fun submitCreditBookingData(creditBookingData: CreditBookingData, cbDimensionData: CBDimensionData, cbInfoData: CBInfoData) {
        _isLoading.value = true
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            if (job?.isActive == false) {
                return@launch
            }
            try {
                val consignmentDetails = repository.getConsignmentDetails(creditBookingData.clientName)
                if (consignmentDetails.isSuccess) {
                    creditBookingData.balanceStock = consignmentDetails.getOrThrow().balanceStock
                    creditBookingData.consignmentNumber = consignmentDetails.getOrThrow().accCode +
                            consignmentDetails.getOrThrow().consignmentNo
                    val result = repository.submitCreditBookingDetails(
                        creditBookingData = creditBookingData,
                        cbDimensionData = cbDimensionData,
                        cbInfoData = cbInfoData
                    )
                    if (result.isSuccess) {
                        _isLoading.value = false
                        _isDataSubmitted.value = true
                    } else {
                        _error.value = result.exceptionOrNull()?.message
                        _isLoading.value = false
                    }
                } else {
                    _error.value = consignmentDetails.exceptionOrNull()?.message
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun createPdf(context: Context) : ByteArray {
        return repository.createPdf(context)
    }

    fun savePdf(pdfData: ByteArray, fileName: String, branch: String) {
        _isLoading.value = true
        _isPdfSaved.value = false
        viewModelScope.launch {
            val isSaved = repository.savePdf(pdfData, fileName, branch, pdfDao)
            if (isSaved) {
                _isLoading.value = false
                _isPdfSaved.value = true
            } else {
                _isLoading.value = false
                _isPdfSaved.value = false
                Toast.makeText(getApplication(), "Failed to save PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearErrorMessage() {
        _error.value = null
    }

    fun createPDFScreenRoute(branch: String): String {
        return Screen.PdfScreen.createRoute(branch)
    }
}
