package com.tpcindia.professionalcouriersapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home/{name}/{branch}") {
        fun createRoute(name: String, branch: String) = "home/$name/$branch"
    }
    object CreditBooking : Screen("credit_booking")
    object CBInfo : Screen("cb_info")
    object CBDimensions : Screen("cb_dimensions")
    object CBPayment : Screen("cb_payment")
}