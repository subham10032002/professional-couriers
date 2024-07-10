package com.tpcindia.professionalcouriersapp.ui.navigation

import android.net.Uri
import com.google.gson.Gson
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData

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
    data object CBInfo : Screen("cb_info/{cbDimensionData}") {
        fun createRoute(cbDimensionsData: CBDimensionData) : String {
            val json = Uri.encode(Gson().toJson(cbDimensionsData))
            return "cb_info/$json"
        }
    }
    data object CBDimensions : Screen("cb_dimensions/{creditBookingData}") {
        fun createRoute(creditBookingData: CreditBookingData): String {
            val json = Uri.encode(Gson().toJson(creditBookingData))
            return "cb_dimensions/$json"
        }
    }
    data object CBPayment : Screen("cb_payment")
}