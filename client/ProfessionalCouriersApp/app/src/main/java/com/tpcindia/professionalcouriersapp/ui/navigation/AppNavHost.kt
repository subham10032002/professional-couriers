package com.tpcindia.professionalcouriersapp.ui.navigation

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.tpcindia.professionalcouriersapp.viewModel.CBInfoViewModel
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel
import com.tpcindia.professionalcouriersapp.viewModel.HomeViewModel
import com.tpcindia.professionalcouriersapp.viewModel.LoginViewModel
import com.tpcindia.professionalcouriersapp.viewModel.PdfViewModel
import com.tpcindia.professionalcouriersapp.viewModel.SharedViewModel

@Composable
fun AppNavHost(
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    creditBookingViewModel: CreditBookingViewModel,
    cbDimensionViewModel: CBDimensionsViewModel,
    cbInfoViewModel: CBInfoViewModel,
    pdfViewModel: PdfViewModel,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Login.route
) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument("name") { defaultValue = "" },
                navArgument("branch") { defaultValue = "" },
                navArgument("branchCode") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val branch = backStackEntry.arguments?.getString("branch") ?: ""
            val branchCode = backStackEntry.arguments?.getString("branchCode") ?: ""
            HomeScreen(
                viewModel = homeViewModel,
                navController = navController,
                name = name,
                branch = branch,
                branchCode = branchCode,
                bookings = listOf("Credit Booking")
            )
        }

        composable(
            route = Screen.CreditBooking.route,
            arguments = listOf(
                navArgument("firmName") { type = NavType.StringType },
                navArgument("day") { type = NavType.StringType },
                navArgument("month") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType },
                navArgument("branch") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val firmName = backStackEntry.arguments?.getString("firmName") ?: ""
            val day = backStackEntry.arguments?.getString("day") ?: ""
            val month = backStackEntry.arguments?.getString("month") ?: ""
            val year = backStackEntry.arguments?.getString("year") ?: ""
            val branch = backStackEntry.arguments?.getString("branch") ?: ""

            val firmNames: List<String> = Gson().fromJson(Uri.decode(firmName), Array<String>::class.java).toList()
            val currentDate = "$day/$month/$year"

            CreditBookingScreen(
                viewModel = creditBookingViewModel,
                sharedViewModel = sharedViewModel,
                navController = navController,
                date = currentDate,
                clientName = firmNames,
                branch = branch
            )
        }

        composable(
            route = Screen.CBDimensions.route
        ) {
            CBDimensionsScreen(
                navController = navController,
                viewModel = cbDimensionViewModel,
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = Screen.CBInfo.route
        ) {
            CBInfoScreen(
                viewModel = cbInfoViewModel,
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = Screen.PdfScreen.route,
            arguments = listOf(
                navArgument("branch") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val branch = backStackEntry.arguments?.getString("branch") ?: ""
            PdfScreen(branch = branch, viewModel = pdfViewModel)

            BackHandler {
                navController.popBackStack(route = Screen.Home.route, inclusive = false)
            }
        }
    }
}
