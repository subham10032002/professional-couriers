package com.tpcindia.professionalcouriersapp.ui.screens

import androidx.compose.runtime.Composable

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
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.InputTextField
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.ShowToastMessage
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.CBInfoViewModel
import com.tpcindia.professionalcouriersapp.viewModel.SharedViewModel

@Composable
fun CBInfoScreen(
    viewModel: CBInfoViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    val scrollState = rememberScrollState()
    val infoState by viewModel.infoState.collectAsState()

    val creditBookingData by sharedViewModel.creditBookingData.collectAsState()
    val cbDimensionData by sharedViewModel.cbDimensionData.collectAsState()
    val updateInvoiceValue = infoState.invoiceValue.toIntOrNull()

    Box(modifier = Modifier.fillMaxSize()) {
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

                LabelText(
                    text = "Invoice Value ",
                    showAsterisk = updateInvoiceValue != null && updateInvoiceValue >= 50000
                )
                InputTextField(
                    value = infoState.invoiceValue,
                    onValueChange = { viewModel.setInvoiceValue(it) },
                    label = "Ex- 560049",
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(15.dp))

                LabelText(
                    text = "Invoice Number ",
                    showAsterisk = updateInvoiceValue != null && updateInvoiceValue >= 50000
                )
                InputTextField(
                    value = infoState.invoiceNumber,
                    onValueChange = { viewModel.setInvoiceNumberValue(it) },
                    label = "Ex- 1234567890"
                )

                Spacer(modifier = Modifier.height(15.dp))

                LabelText(
                    text = "E-waybill ",
                    showAsterisk = updateInvoiceValue != null && updateInvoiceValue >= 50000
                )
                InputTextField(
                    value = infoState.ewaybill,
                    onValueChange = { viewModel.setEwaybillValue(it) },
                    label = "Ex- 1234567890",
                    maxLength = 12,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(15.dp))

                LabelText(
                    text = "Product ",
                    showAsterisk = updateInvoiceValue != null && updateInvoiceValue >= 50000
                )
                InputTextField(
                    value = infoState.product,
                    onValueChange = { viewModel.setProductValue(it) },
                    label = "Ex- 1234567890"
                )

                Spacer(modifier = Modifier.height(20.dp))

                CustomButton(
                    onClick = {
                        viewModel.onButtonClicked(
                            creditBookingData = creditBookingData.copy(),
                            cbDimensionData = cbDimensionData.copy()
                        )
                    },
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

        if (infoState.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
            ) {
                CircularProgressIndicator(color = Color.White)
                Text(
                    text = "Please wait, we're submitting the details...",
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        infoState.error?.let { errorMessage ->
            ShowToastMessage(errorMessage)
            viewModel.clearErrorMessage()
        }

        if (infoState.isPdfSaved) {
            viewModel.clearPDFSavedState()
            val route = viewModel.createPDFScreenRoute(uniqueUser = creditBookingData.username+creditBookingData.userCode)
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

        LaunchedEffect(infoState.isDataSubmitted && infoState.pdfAddress != null) {
            if (infoState.isDataSubmitted) {
                viewModel.clearDataSubmitted()
                try {
                    val fileName = "${infoState.consignmentNumber}.pdf"
                    viewModel.savePdf(infoState.pdfAddress!!, fileName, uniqueUser = creditBookingData.username+creditBookingData.userCode)
                } catch (e: Exception) {
                    // Handle Exception
                }
            }
        }
    }

}

