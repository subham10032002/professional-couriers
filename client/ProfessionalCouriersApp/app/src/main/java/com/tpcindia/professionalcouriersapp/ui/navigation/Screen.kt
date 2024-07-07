package com.tpcindia.professionalcouriersapp.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home/{name}/{branch}") {
        fun createRoute(name: String, branch: String) = "home/$name/$branch"
    }
    data object CreditBooking : Screen("credit_booking/{consignmentNumber}/{firmName}/{day}/{month}/{year}/{balanceStock}") {
        fun createRoute(
            consignmentNumber: String,
            firmName: String,
            day: String,
            month: String,
            year: String,
            balanceStock: String
        ) = "credit_booking/$consignmentNumber/$firmName/$day/$month/$year/$balanceStock"
    }
    data object CBInfo : Screen("cb_info")
    data object CBDimensions : Screen("cb_dimensions")
    data object CBPayment : Screen("cb_payment")
}