package com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tpcindia.professionalcouriersapp.viewModel.LoginViewModel

class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}