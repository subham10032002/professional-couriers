package com.tpcindia.professionalcouriersapp.data.model

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val name: String? = null,
    val branch: String? = null
)