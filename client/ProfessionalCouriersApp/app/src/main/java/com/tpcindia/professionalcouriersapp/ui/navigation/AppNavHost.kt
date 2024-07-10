package com.tpcindia.professionalcouriersapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.ui.screens.*
import com.tpcindia.professionalcouriersapp.viewModel.CBDimensionsViewModel
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel
import com.tpcindia.professionalcouriersapp.viewModel.HomeViewModel
import com.tpcindia.professionalcouriersapp.viewModel.LoginViewModel

@Composable
fun AppNavHost(
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    creditBookingViewModel: CreditBookingViewModel,
    cbDimensionViewModel: CBDimensionsViewModel,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Login.route
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument("name") { defaultValue = "" },
                navArgument("branch") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val branch = backStackEntry.arguments?.getString("branch") ?: ""
            HomeScreen(
                viewModel = homeViewModel,
                navController = navController,
                name = name,
                branch = branch,
                bookings = listOf("Credit Booking", "Cash Booking")
            )
        }

        composable(
            route = Screen.CreditBooking.route,
            arguments = listOf(
                navArgument("consignmentNumber") { type = NavType.StringType },
                navArgument("firmName") { type = NavType.StringType },
                navArgument("day") { type = NavType.StringType },
                navArgument("month") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType },
                navArgument("balanceStock") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val consignmentNumber = backStackEntry.arguments?.getString("consignmentNumber") ?: ""
            val firmName = backStackEntry.arguments?.getString("firmName") ?: ""
            val day = backStackEntry.arguments?.getString("day") ?: ""
            val month = backStackEntry.arguments?.getString("month") ?: ""
            val year = backStackEntry.arguments?.getString("year") ?: ""
            val balanceStock = backStackEntry.arguments?.getString("balanceStock") ?: ""

            val currentDate = "$day/$month/$year" // Construct date string

            CreditBookingScreen(
                viewModel = creditBookingViewModel,
                navController = navController,
                date = currentDate,
                consignmentNumber = consignmentNumber,
                balanceStock = balanceStock,
                clientName = firmName
            )
        }

        composable(
            route = Screen.CBDimensions.route,
            arguments = listOf(navArgument("creditBookingData") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("creditBookingData") ?: ""
            val creditBookingData = Gson().fromJson(json, CreditBookingData::class.java)
            CBDimensionsScreen(navController = navController, viewModel = cbDimensionViewModel, creditBookingData = creditBookingData)
        }

        composable(
            route = Screen.CBInfo.route,
            arguments = listOf(navArgument("cbDimensionData") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("cbDimensionData") ?: ""
            val cbDimensionData = Gson().fromJson(json, CBDimensionData::class.java)
            CBInfoScreen()
        }
    }
}