package com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel

class CreditBookingViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreditBookingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreditBookingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}