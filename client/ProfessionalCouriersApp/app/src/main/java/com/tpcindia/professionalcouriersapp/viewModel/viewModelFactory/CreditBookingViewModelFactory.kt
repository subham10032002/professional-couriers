package com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel

class CreditBookingViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreditBookingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreditBookingViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}