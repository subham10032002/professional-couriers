package com.tpcindia.professionalcouriersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpcindia.professionalcouriersapp.ui.navigation.AppNavHost
import com.tpcindia.professionalcouriersapp.ui.theme.ProfessionalCouriersAppTheme
import com.tpcindia.professionalcouriersapp.viewModel.CBDimensionsViewModel
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel
import com.tpcindia.professionalcouriersapp.viewModel.HomeViewModel
import com.tpcindia.professionalcouriersapp.viewModel.LoginViewModel
import com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory.CBDimensionViewModelFactory
import com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory.CreditBookingViewModelFactory
import com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory.HomeViewModelFactory
import com.tpcindia.professionalcouriersapp.viewModel.viewModelFactory.LoginViewModelFactory

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(application)
    }

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory()
    }

    private val creditBookingViewModel: CreditBookingViewModel by viewModels {
        CreditBookingViewModelFactory()
    }

    private val cbDimensionsViewModel: CBDimensionsViewModel by viewModels {
        CBDimensionViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfessionalCouriersAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    private fun AppContent(modifier: Modifier) {
        AppNavHost(
            loginViewModel = loginViewModel,
            homeViewModel = homeViewModel,
            creditBookingViewModel = creditBookingViewModel,
            cbDimensionViewModel = cbDimensionsViewModel,
            modifier = modifier
        )
    }
}
