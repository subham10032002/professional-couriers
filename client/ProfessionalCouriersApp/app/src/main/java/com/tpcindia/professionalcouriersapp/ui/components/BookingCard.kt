package com.tpcindia.professionalcouriersapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.ui.screens.HomeScreen

@Composable
fun BookingCard(booking: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = booking,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Preview
@Composable
fun BookingCardPreview() {
    BookingCard("Credit Booking")
}
