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
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument("name") { defaultValue = "" },
                navArgument("branch") { defaultValue = "" },
                navArgument("branchCode") { defaultValue = "" } ,
                navArgument("userCode") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val branch = backStackEntry.arguments?.getString("branch") ?: ""
            val branchCode = backStackEntry.arguments?.getString("branchCode") ?: ""
            val userCode = backStackEntry.arguments?.getString("userCode") ?: ""
            HomeScreen(
                viewModel = homeViewModel,
                sharedViewModel = sharedViewModel,
                navController = navController,
                name = name,
                branch = branch,
                branchCode = branchCode,
                bookings = listOf("Credit Booking"),
                userCode = userCode
            )
        }

        composable(
            route = Screen.CreditBooking.route,
        ) {
            CreditBookingScreen(
                viewModel = creditBookingViewModel,
                sharedViewModel = sharedViewModel,
                navController = navController
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
                navArgument("uniqueUser") { defaultValue = "" },
                navArgument("branch") { defaultValue = "" },
                navArgument("userCode") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val uniqueUser = backStackEntry.arguments?.getString("uniqueUser") ?: ""
            val branch = backStackEntry.arguments?.getString("branch") ?: ""
            val userCode = backStackEntry.arguments?.getString("userCode") ?: ""
            PdfScreen(uniqueUser = uniqueUser, branch = branch, userCode = userCode, viewModel = pdfViewModel)

            BackHandler {
                navController.popBackStack(route = Screen.Home.route, inclusive = false)
            }
        }
    }
}
