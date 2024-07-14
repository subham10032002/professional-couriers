package com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tpcindia.professionalcouriersapp.viewModel.CBDimensionsViewModel

class CBDimensionViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CBDimensionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CBDimensionsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}