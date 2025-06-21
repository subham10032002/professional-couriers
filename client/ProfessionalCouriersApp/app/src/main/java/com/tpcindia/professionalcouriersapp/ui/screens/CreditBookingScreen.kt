package com.tpcindia.professionalcouriersapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.tpcindia.professionalcouriersapp.data.model.response.DestinationDetails
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.DropdownTextField
import com.tpcindia.professionalcouriersapp.ui.components.ImagePickerBox
import com.tpcindia.professionalcouriersapp.ui.components.InputTextField
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.ShowToastMessage
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.CreditBookingViewModel
import com.tpcindia.professionalcouriersapp.viewModel.SharedViewModel

@Composable
fun CreditBookingScreen(
    viewModel: CreditBookingViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    var consigneeName by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("") }
    var consigneeType by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var noOfPsc by remember { mutableStateOf("") }
    var selectedImageByteArray by remember { mutableStateOf<ByteArray?>(null) }
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    val creditBookingState by viewModel.creditBookingState.collectAsState()
    val homeScreenData by sharedViewModel.homeScreenData.collectAsState()

    var currentDate = homeScreenData.currentDate
    val clientDetailsList = homeScreenData.clientDetails
    var selectedClientDetails by remember { mutableStateOf(clientDetailsList.firstOrNull()) }

    val destinationCities = creditBookingState.destinationOptions.map { it.city }.toMutableList()
    var selectedDestination by remember { mutableStateOf<DestinationDetails?>(null) }

    LaunchedEffect(true) {
        viewModel.clearState()
    }

    // Function to check if all mandatory fields are filled
    fun areAllMandatoryFieldsFilled(): Boolean {
        return when {
            currentDate.isBlank() -> false
            selectedClientDetails?.firmName?.isBlank() == true -> false
            consigneeName.isBlank() -> false
            pincode.isBlank() -> false
            destination.isBlank() -> false
            consigneeType.isBlank() -> false
            mode.isBlank() -> false
            consigneeName.isBlank() -> false
            creditBookingState.weight.isBlank() -> false
            selectedImageByteArray == null || selectedImageByteArray?.isEmpty() == true -> false
            else -> true
        }
    }

    fun getCreditBookingData(): CreditBookingData {
        val creditBookingData = CreditBookingData(
            username = homeScreenData.username,
            userCode = homeScreenData.userCode,
            currentDate = currentDate,
            consigneeName = consigneeName,
            mode = mode,
            consigneeType = consigneeType,
            pincode = pincode,
            destination = destination,
            destDetails = selectedDestination ?: DestinationDetails(),
            clientName = selectedClientDetails?.firmName ?: "",
            clientAddress = selectedClientDetails?.clientAddress ?: "" ,
            clientContact = selectedClientDetails?.clientContactNo ?: "",
            masterCompanyCode = selectedClientDetails?.masterCompanyCode ?: "",
            noOfPsc = noOfPsc.substringBefore("."),
            weight = creditBookingState.weight.toFloat().toString(),
            bookingDate = currentDate,
            photoOfAddress = selectedImageByteArray,
            branch = homeScreenData.branch,
            consignmentNumber = creditBookingState.consignmentNumber,
            balanceStock = creditBookingState.balanceStock,
            masterAddressDetails = creditBookingState.masterAddressDetails,
            pdfAddress = creditBookingState.pdfAddress,
            transactionId = homeScreenData.transactionId,
        )
        return creditBookingData
    }

    fun navigateToCBDimensionScreen() {
        viewModel.setLoading(true)
        if (areAllMandatoryFieldsFilled()) {
            val creditBookingData = getCreditBookingData()
            sharedViewModel.setCreditBookingData(creditBookingData)
            navController.navigate(viewModel.createCBDimensionRoute())
            viewModel.clearState()
        } else {
            Toast.makeText(context, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show()
        }
        viewModel.setLoading(false)
    }

    fun submitCreditBookingData() {
        if (areAllMandatoryFieldsFilled()) {
            val creditBookingData = getCreditBookingData()
            sharedViewModel.setCreditBookingData(creditBookingData)
            viewModel.onButtonClicked(creditBookingData)
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

            LabelText("Client Name ")
            DropdownTextField(
                label = "Select..",
                options = clientDetailsList.map { it.firmName },
                selectedOption = selectedClientDetails?.firmName ?: "",
                onOptionSelected = { selectedFirmName ->
                    selectedClientDetails = clientDetailsList.first { it.firmName == selectedFirmName }
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Booking Date ")
            InputTextField(
                value = currentDate,
                onValueChange = { currentDate = it },
                label = "Booking Date",
                readOnly = true
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
                options = destinationCities,
                selectedOption = destination,
                onOptionSelected = {
                    destination = it
                    selectedDestination = creditBookingState.destinationOptions.firstOrNull { destDetails ->
                        destDetails.city == destination
                    }
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Consignment Type ")
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
            ImagePickerBox(onImagePicked = { byteArray ->
                selectedImageByteArray = byteArray
            })

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Consignee Name ")
            InputTextField(
                value = consigneeName,
                onValueChange = { consigneeName = it },
                label = "Ex- 1234567890"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("No of Psc ", false)
            InputTextField(
                value = noOfPsc,
                onValueChange = { noOfPsc = it },
                label = "Ex- 5",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Weight ", true)
            InputTextField(
                value = creditBookingState.weight,
                onValueChange = { viewModel.onWeightChanged(it) },
                label = "Ex- 10",
                allowDecimals = 3,
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                onClick = { if (creditBookingState.submitEnabled) submitCreditBookingData() else  navigateToCBDimensionScreen() },
                horizontalPadding = 60.dp,
                isFilled = true,
                loginState = false,
                text = if (creditBookingState.submitEnabled) "Submit" else "Next",
                textColor = Color.White,
                backgroundColor = Red
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (creditBookingState.isPdfSaved) {
            val route = viewModel.createPDFScreenRoute(
                uniqueUser = homeScreenData.username+homeScreenData.userCode,
                branch = homeScreenData.branch,
                userCode = homeScreenData.userCode,
            )
            route.let {
                viewModel.clearState()
                sharedViewModel.clearState()
                navController.navigate(route) {
                    popUpTo(route = Screen.Home.route) {
                        inclusive = false
                    }
                }
            }
        }


        if (creditBookingState.isDataSubmitted && creditBookingState.pdfAddress != null) {
            try {
                creditBookingState.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
                val creditBookingData = getCreditBookingData()
                val fileName = "${creditBookingData.consignmentNumber}.pdf"
                viewModel.savePdf(creditBookingState.pdfAddress!!, fileName, uniqueUser = homeScreenData.username+homeScreenData.userCode)
            } catch (e: Exception) {
                // Handle Exception
            }
        }


        creditBookingState.error?.let { errorMessage ->
            ShowToastMessage(errorMessage)
            viewModel.clearErrorMessage()
        }
    }

    if (creditBookingState.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = false, onClick = { /* Do nothing */ })
                .background(Color(0x80000000))
        ) {
            CircularProgressIndicator(color = Color.White)
            Text(
                text = "Please wait, we're processing...",
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
