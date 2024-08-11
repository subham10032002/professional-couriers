package com.tpcindia.professionalcouriersapp.ui.navigation

import android.net.Uri
import com.google.gson.Gson
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home/{name}/{branch}/{branchCode}/{userCode}") {
        fun createRoute(
            name: String,
            branch: String,
            branchCode: String,
            userCode: String
        ) = "home/$name/$branch/$branchCode/$userCode"
    }
    data object CreditBooking : Screen("credit_booking/{firmName}/{day}/{month}/{year}/{branch}/{username}/{userCode}") {
        fun createRoute(
            firmNames: List<String>,
            day: String,
            month: String,
            year: String,
            branch: String,
            username: String,
            userCode: String
        ): String {
            val jsonFirmNames = Uri.encode(Gson().toJson(firmNames))
            return "credit_booking/$jsonFirmNames/$day/$month/$year/$branch/$username/$userCode"
        }
    }
    data object CBInfo : Screen("cb_info")
    data object CBDimensions : Screen("cb_dimensions")
    data object PdfScreen : Screen("pdf_screen/{branch}") {
        fun createRoute(branch: String) = "pdf_screen/$branch"
    }
}