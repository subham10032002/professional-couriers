package com.tpcindia.professionalcouriersapp.ui.screens

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
import com.tpcindia.professionalcouriersapp.ui.components.DatePickerInputField
import com.tpcindia.professionalcouriersapp.ui.components.DropdownTextField
import com.tpcindia.professionalcouriersapp.ui.components.ImagePickerBox
import com.tpcindia.professionalcouriersapp.ui.components.InputTextField
import com.tpcindia.professionalcouriersapp.ui.components.MandatoryLabelText
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.theme.Red

@Composable
fun CreditBookingScreen() {
    var selectedDate by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    var consignmentNumber by remember { mutableStateOf("") }
    var consigneeName by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("") }
    var consigneeType by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var selectedClientName by remember { mutableStateOf("") }
    var noOfPsc by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
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
            MandatoryLabelText("Date ")
            InputTextField(
                value = currentDate,
                onValueChange = { currentDate = it },
                label = "Today's Date"
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Consignment Number ")
            InputTextField(
                value = consignmentNumber,
                onValueChange = { consignmentNumber = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Client Name ")
            DropdownTextField(
                label = "Select..",
                options = mutableListOf("ab", "abcd"),
                selectedOption = selectedClientName,
                onOptionSelected = { selectedClientName = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Booking Date ")
            DatePickerInputField(
                label = "Ex- 15/07/2023",
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Pincode ")
            InputTextField(
                value = pincode,
                onValueChange = { pincode = it },
                label = "Ex- 560049"
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Destination ")
            DropdownTextField(
                label = "Select..",
                options = mutableListOf("ab", "abcd"),
                selectedOption = destination,
                onOptionSelected = { destination = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Consignee Type ")
            DropdownTextField(
                label = "Select..",
                options = mutableListOf("ab", "abcd"),
                selectedOption = consigneeType,
                onOptionSelected = { consigneeType = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Mode ")
            DropdownTextField(
                label = "Select..",
                options = mutableListOf("ab", "abcd"),
                selectedOption = mode,
                onOptionSelected = { mode = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Photo of address ")
            ImagePickerBox()

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Consignee Name ")
            InputTextField(
                value = consigneeName,
                onValueChange = { consigneeName = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("No of Psc ")
            InputTextField(
                value = noOfPsc,
                onValueChange = { noOfPsc = it },
                label = "Ex- 5"
            )

            Spacer(modifier = Modifier.height(15.dp))

            MandatoryLabelText("Weight ", false)
            InputTextField(
                value = weight,
                onValueChange = { weight = it },
                label = "Ex- 10"
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                onClick = {  },
                horizontalPadding = 80.dp,
                isFilled = true, // Check for all the mandatory fields are filled or Not
                loginState = false,
                text = "Next",
                textColor = Color.White,
                backgroundColor = Red
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun CreditBookingScreenPreview() {
    CreditBookingScreen()
}
