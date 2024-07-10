package com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tpcindia.professionalcouriersapp.viewModel.CBInfoViewModel

class CBInfoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CBInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CBInfoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}