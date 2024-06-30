package com.tpcindia.professionalcouriersapp.ui.screens

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.InputTextField
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.theme.Red

@Composable
fun CBInfoScreen() {
    var invoiceNumber by remember { mutableStateOf("") }
    var product by remember { mutableStateOf("") }
    var ewaybill by remember { mutableStateOf("") }
    var declaredValue by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {

        TopBanner()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Credit Booking",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            // Date * header
            LabelText("Invoice Number ", false)
            InputTextField(
                value = invoiceNumber,
                onValueChange = { invoiceNumber = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Product ", false)
            InputTextField(
                value = product,
                onValueChange = { product = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Declared Value ", false)
            InputTextField(
                value = declaredValue,
                onValueChange = { declaredValue = it },
                label = "Ex- 560049"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Ewaybill ", false)
            InputTextField(
                value = ewaybill,
                onValueChange = { ewaybill = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                onClick = {  },
                horizontalPadding = 60.dp,
                isFilled = true,
                loginState = false,
                text = "Submit",
                textColor = Color.White,
                backgroundColor = Red
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun CBInfoScreenPreview() {
    CBInfoScreen()
}
