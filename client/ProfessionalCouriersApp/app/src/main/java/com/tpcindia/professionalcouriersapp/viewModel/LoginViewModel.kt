package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.request.LoginRequest
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
        viewModelScope.launch(Dispatchers.Main) {
            val savedUser = repository.getUser(getApplication())
            if (savedUser != null) {
                _loginState.value = LoginState(
                    isAuthenticated = true,
                    name = "${savedUser.firstName} ${savedUser.lastName}",
                    branch = savedUser.branch,
                    branchCode = savedUser.branchCode,
                    userCode = savedUser.userCode
                )
            }
        }
    }

    fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        _loginState.value = LoginState(isLoading = true)
        viewModelScope.launch(Dispatchers.Main) {
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
                        branch = user.branch,
                        branchCode = user.branchCode,
                        userCode = user.userCode
                    )
                } else {
                    _loginState.value = LoginState(error = result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState(error = e.message)
            }
        }
    }

    fun createHomeScreenRoute(): String? {
        val branch = _loginState.value.branch
        val branchCode = _loginState.value.branchCode
        val name = _loginState.value.name
        val userCode = _loginState.value.userCode
        if (branch == null || branchCode == null || name == null || userCode == null) {
            return null
        }
        return Screen.Home.createRoute(
            name = name,
            branch = branch,
            branchCode = branchCode,
            userCode = userCode
        )
    }

    fun clearState() {
        _loginState.value = LoginState()
    }
}
