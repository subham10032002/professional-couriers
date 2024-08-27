package com.tpcindia.professionalcouriersapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.ui.components.BookingCard
import com.tpcindia.professionalcouriersapp.ui.theme.*
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.data.model.MenuItem
import com.tpcindia.professionalcouriersapp.ui.components.LocationSelectionDialog
import com.tpcindia.professionalcouriersapp.ui.components.ShowToastMessage
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.viewModel.HomeViewModel
import com.tpcindia.professionalcouriersapp.viewModel.SharedViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavController,
    name: String,
    branch: String,
    branchCode: String,
    bookings: List<String>,
    userCode: String
) {
    var fabState by remember { mutableStateOf(FabState.Default) }
    var rotation by remember { mutableFloatStateOf(0f) }
    val homeState by viewModel.homeState.collectAsState()
    val context = LocalContext.current

    val fabIcon = when (fabState) {
        FabState.Default -> painterResource(id = R.drawable.tpc_outline_email_24)
        FabState.Success -> painterResource(id = R.drawable.tpc_verified)
        else -> null
    }

    LaunchedEffect(fabState) {
        if (fabState == FabState.Loading) {
            while (true) {
                rotation += 10f
                delay(16L)
                if (fabState != FabState.Loading) break
            }
        } else if (fabState == FabState.Success) {
            delay(1000)
            fabState = FabState.Default
        }
    }

    fun onEmailButtonClicked() {
        viewModel.sendEmails(
            branch = branch,
            branchCode = branchCode,
            userName = name
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopBanner(showMenuIcon = true, menuItem = listOf(
                MenuItem("PDFs") {
                    val pdfScreenRoute = viewModel.createPDFScreenRoute(branch = branch)
                    navController.navigate(pdfScreenRoute)
                },
                MenuItem("Logout") {
                    viewModel.logout()
                    val loginScreenRoute = viewModel.getLoginScreenRoute()
                    navController.navigate(loginScreenRoute) {
                        popUpTo(viewModel.getHomeScreenRoute()) { inclusive = true }
                    }
                })
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Card with gradient stroke, contact image, name, and branch
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    1.dp, Brush.horizontalGradient(
                        colors = listOf(GradientLeft, GradientRight)
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tpc_contact_new),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = branch, fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // "Select Booking" text
            Text(
                text = "Select Booking",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Scrollable list of Bookings
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                bookings.forEach { booking ->
                    BookingCard(booking = booking, onClick = {
                        viewModel.onBookingCardClicked()
                    })
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = {
                onEmailButtonClicked()
            },
            containerColor = Color.Red,
            contentColor = Color.White,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                if (fabState == FabState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    if (fabIcon != null) {
                        Icon(
                            painter = fabIcon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Email",
                        fontSize = 18.sp,
                    )
                }
            }
        }

        if (homeState.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
            ) {
                CircularProgressIndicator(color = Color.White)
                Text(
                    text = "Please wait, we're fetching details...",
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        if (homeState.isBookingCardClicked) {
            viewModel.clearBookingCardClicked()
            LocationSelectionDialog(
                onLocationSelected = { hasLocationPermission ->
                    if (hasLocationPermission) {
                        viewModel.onBookingClick(branch = branch)
                    } else {
                        Toast.makeText(context, "Location permission is required", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        homeState.error?.let { errorMessage ->
            ShowToastMessage(errorMessage)
            viewModel.clearErrorMessage()
        }

        if (homeState.isEmailSending) {
            fabState = FabState.Loading
            Toast.makeText(context, "Sending email...\nPlease don't press anything", Toast.LENGTH_SHORT).show()
            viewModel.clearState()
        }
        if (homeState.emailSentSuccessfully) {
            fabState = FabState.Success
            Toast.makeText(context, "Email sent successfully", Toast.LENGTH_SHORT).show()
            viewModel.clearState()
        }
        if (homeState.emailSendingFailed) {
            fabState = FabState.Default
            viewModel.clearState()
        }

        if (homeState.isDataFetched) {
            val route = viewModel.createCreditBookingScreenRoute(
                branch = branch,
                userName = name,
                userCode = userCode,
                sharedViewModel = sharedViewModel
            )
            viewModel.clearState()
            navController.navigate(route)
        }
    }
}

enum class FabState {
    Default, Loading, Success
}


