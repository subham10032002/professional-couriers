package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.LoginRequest
import com.tpcindia.professionalcouriersapp.data.model.User
import com.tpcindia.professionalcouriersapp.data.repository.LoginRepository
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.viewModel.uiState.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val repository: LoginRepository = LoginRepository(NetworkService())

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        val savedUser = repository.getUser(getApplication())
        if (savedUser != null) {
            _loginState.value = LoginState(isAuthenticated = true, name = "${savedUser.firstName} ${savedUser.lastName}", branch = savedUser.branchCode)
        }
    }

    fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        _loginState.value = LoginState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.login(loginRequest)
                if (result.isSuccess) {
                    val user = result.getOrThrow()
                    repository.saveUser(
                        getApplication(),
                        user
                    )
                    _loginState.value = LoginState(
                        isAuthenticated = true,
                        name = "${user.firstName} ${user.lastName}",
                        branch = user.branchCode
                    )
                } else {
                    _loginState.value = LoginState(error = result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState(error = e.message)
            }
        }
    }

    // Function to create navigation route to HomeScreen
    fun createHomeScreenRoute(): String? {
        return _loginState.value.name?.let { name ->
            _loginState.value.branch?.let { branch ->
                Screen.Home.createRoute(name, branch)
            }
        }
    }
}
