package com.tpcindia.professionalcouriersapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tpcindia.professionalcouriersapp.ui.screens.*
import com.tpcindia.professionalcouriersapp.viewModel.LoginViewModel

@Composable
fun AppNavHost(
    loginViewModel: LoginViewModel,
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
            HomeScreen(navController = navController, name = name, branch = branch, bookings = listOf("Credit Booking", "Cash Booking"))
        }
    }
}
