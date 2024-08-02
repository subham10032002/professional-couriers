package com.tpcindia.professionalcouriersapp.ui.navigation

import android.net.Uri
import com.google.gson.Gson
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home/{name}/{branch}/{branchCode}") {
        fun createRoute(
            name: String,
            branch: String,
            branchCode: String
        ) = "home/$name/$branch/$branchCode"
    }
    data object CreditBooking : Screen("credit_booking/{firmName}/{day}/{month}/{year}/{branch}") {
        fun createRoute(
            firmNames: List<String>,
            day: String,
            month: String,
            year: String,
            branch: String
        ): String {
            val jsonFirmNames = Uri.encode(Gson().toJson(firmNames))
            return "credit_booking/$jsonFirmNames/$day/$month/$year/$branch"
        }
    }
    data object CBInfo : Screen("cb_info")
//    {
//        fun createRoute(cbDimensionsData: CBDimensionData, bookingData: CreditBookingData) : String {
//            val json1 = Uri.encode(Gson().toJson(cbDimensionsData))
//            val json2 = Uri.encode(Gson().toJson(bookingData))
//            return "cb_info/$json1/$json2"
//        }
//    }
    data object CBDimensions : Screen("cb_dimensions")
//    {
//        fun createRoute(creditBookingData: CreditBookingData): String {
//            val json = Uri.encode(Gson().toJson(creditBookingData))
//            return "cb_dimensions/$json"
//        }
//    }
    data object PdfScreen : Screen("pdf_screen/{branch}") {
        fun createRoute(branch: String) = "pdf_screen/$branch"
    }
}