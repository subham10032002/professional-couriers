package com.tpcindia.professionalcouriersapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.configs.UIConfig
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.DatePickerInputField
import com.tpcindia.professionalcouriersapp.ui.components.DropdownTextField
import com.tpcindia.professionalcouriersapp.ui.components.ImagePickerBox
import com.tpcindia.professionalcouriersapp.ui.components.InputTextField
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.ShowToastMessage
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel

@Composable
fun CreditBookingScreen(
    viewModel: CreditBookingViewModel,
    navController: NavController,
    date: String,
    clientName: List<String>
) {
    var selectedDate by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf(date) }
    var consigneeName by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("") }
    var consigneeType by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var selectedClientName by remember { mutableStateOf(clientName.first()) }
    var noOfPsc by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    val creditBookingState by viewModel.creditBookingState.collectAsState()

    val options = remember { mutableStateListOf<String>() }
    val selectedOption = remember { mutableStateOf("") }

    // Effect to update options and selectedOption when creditBookingState changes
    LaunchedEffect(creditBookingState.destinationOptions) {
        if (creditBookingState.destinationOptions.isNotEmpty()) {
            options.clear()
            options.addAll(creditBookingState.destinationOptions.map { it.cities })

            // Select the first option if available
            if (options.contains(destination)) {
                selectedOption.value = destination
            } else {
                selectedOption.value = options.firstOrNull() ?: ""
                destination = selectedOption.value // Ensure destination state is updated
            }
        }
    }

    // Function to check if all mandatory fields are filled
    fun areAllMandatoryFieldsFilled(): Boolean {
        return when {
            currentDate.isBlank() -> false
            selectedClientName.isBlank() -> false
            selectedDate.isBlank() -> false
            consigneeName.isBlank() -> false
            pincode.isBlank() -> false
            destination.isBlank() -> false
            consigneeType.isBlank() -> false
            mode.isBlank() -> false
            consigneeName.isBlank() -> false
            noOfPsc.isBlank() -> false
            else -> true
        }
    }

    fun navigateToCreditInfoScreen() {
        if (areAllMandatoryFieldsFilled()) {
            val creditBookingData = CreditBookingData(
                currentDate = currentDate,
                consigneeName = consigneeName,
                mode = mode,
                consigneeType = consigneeType,
                pincode = pincode,
                destination = destination,
                clientName = selectedClientName,
                noOfPsc = noOfPsc,
                weight = weight,
                bookingDate = selectedDate
            )
            navController.navigate(viewModel.createCBDimensionRoute(bookingData = creditBookingData))
        } else {
            Toast.makeText(context, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show()
        }
    }


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
            LabelText("Date ")
            InputTextField(
                value = currentDate,
                onValueChange = { currentDate = it },
                label = "Today's Date"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Client Name ")
            DropdownTextField(
                label = "Select..",
                options = clientName,
                selectedOption = selectedClientName,
                onOptionSelected = { selectedClientName = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Booking Date ")
            DatePickerInputField(
                label = "Ex- 15/07/2023",
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Pincode ")
            InputTextField(
                value = pincode,
                onValueChange = {
                    if (it.length <= 6) {
                        pincode = it
                        if (it.length == 6) {
                            viewModel.fetchDestination(it)
                        }
                    }},
                label = "Ex- 560049",
                keyboardType = KeyboardType.Number,
                maxLength = 6
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Destination ")
            DropdownTextField(
                label = "Select..",
                options = options,
                selectedOption = selectedOption.value,
                onOptionSelected = { destination = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Consignee Type ")
            DropdownTextField(
                label = "Select..",
                options = UIConfig.CONSIGNEE_TYPE,
                selectedOption = consigneeType,
                onOptionSelected = { consigneeType = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Mode ")
            DropdownTextField(
                label = "Select..",
                options = UIConfig.MODE,
                selectedOption = mode,
                onOptionSelected = { mode = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Photo of address ")
            ImagePickerBox()

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Consignee Name ")
            InputTextField(
                value = consigneeName,
                onValueChange = { consigneeName = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("No of Psc ")
            InputTextField(
                value = noOfPsc,
                onValueChange = { noOfPsc = it },
                label = "Ex- 5",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Weight ", false)
            InputTextField(
                value = weight,
                onValueChange = { weight = it },
                label = "Ex- 10"
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                onClick = {  navigateToCreditInfoScreen() },
                horizontalPadding = 60.dp,
                isFilled = true,
                loginState = false,
                text = "Next",
                textColor = Color.White,
                backgroundColor = Red
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (creditBookingState.isLoading) {
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

        creditBookingState.error?.let { errorMessage ->
            ShowToastMessage(errorMessage)
            viewModel.clearErrorMessage()
        }
    }
}
