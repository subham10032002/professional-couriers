package com.tpcindia.professionalcouriersapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.ui.components.BookingCard
import com.tpcindia.professionalcouriersapp.ui.theme.*
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController, name: String, branch: String, bookings: List<String>) {

    var fabState by remember { mutableStateOf(FabState.Default) }

    val isNetworkCallInProgress = fabState == FabState.Loading

    var rotation by remember { mutableFloatStateOf(0f) }


    val fabIcon = when (fabState) {
        FabState.Default -> painterResource(id = R.drawable.tpc_outline_email_24)
        FabState.Loading -> painterResource(id = R.drawable.tpc_progress_bar_mail)
        FabState.Success -> painterResource(id = R.drawable.tpc_verified)
        FabState.Failure -> painterResource(id = R.drawable.tpc_verified)
    }

    LaunchedEffect(fabState) {
        while (isNetworkCallInProgress) {
            rotation += 10f
            delay(16L)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Top rectangle with gradient and image
            TopBanner()

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
                        painter = painterResource(id = R.drawable.tpc_contact_new), // Replace with your contact image resource
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
                    BookingCard(booking = booking)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = {
                if (!isNetworkCallInProgress) {
//                        coroutineScope.launch {
                    fabState = FabState.Loading
                    // Do network call
//                        delay(2000)
                    val success = true
                    fabState = if (success) FabState.Success else FabState.Failure
//                        delay(3000) // Delay for some second and fallback to default state
                    fabState = FabState.Default
//                        }
                }
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
                Icon(
                    painter = fabIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(if (isNetworkCallInProgress) rotation else 0f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Email",
                    fontSize = 18.sp,
                )
            }
        }
    }
}

enum class FabState {
    Default, Loading, Success, Failure
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(context = LocalContext.current), "Name", "branch", mutableListOf("Credit Booking", "Cash Booking"))
}
