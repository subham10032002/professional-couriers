package com.tpcindia.professionalcouriersapp.viewModel

import com.tpcindia.professionalcouriersapp.data.model.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        _loginState.value = LoginState(isLoading = true)
        CoroutineScope(Dispatchers.Main).launch {
            // Simulate network request
            delay(2000)
            if (username == "test" && password == "password") {
                _loginState.value = LoginState(isAuthenticated = true, name = "John Doe", branch = "Sales")
            } else {
                _loginState.value = LoginState(error = "Authentication failed")
            }
        }
    }

}