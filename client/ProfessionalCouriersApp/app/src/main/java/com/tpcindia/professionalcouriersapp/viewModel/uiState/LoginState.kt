package com.tpcindia.professionalcouriersapp.viewModel.uiState

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val name: String? = null,
    val branch: String? = null,
    val branchCode: String? = null
)