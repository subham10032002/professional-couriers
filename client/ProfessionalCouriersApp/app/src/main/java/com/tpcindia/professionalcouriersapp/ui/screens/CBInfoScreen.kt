package com.tpcindia.professionalcouriersapp.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.InputTextField
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.ShowToastMessage
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.CBInfoViewModel

@Composable
fun CBInfoScreen(
    viewModel: CBInfoViewModel,
    navController: NavController,
    creditBookingData: CreditBookingData,
    cbDimensionData: CBDimensionData
) {
    var invoiceNumber by remember { mutableStateOf("") }
    var product by remember { mutableStateOf("") }
    var ewaybill by remember { mutableStateOf("") }
    var declaredValue by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val submitDetailsState by viewModel.submitDetailsState.collectAsState()

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
                    onClick = {
                        if (!submitDetailsState.isLoading) {
                            viewModel.submitCreditBookingData(
                                creditBookingData = creditBookingData,
                                cbDimensionData = cbDimensionData,
                                cbInfoData = CBInfoData(
                                    invoiceNumber,
                                    product,
                                    declaredValue,
                                    ewaybill
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please wait we're submitting the data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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

        if (submitDetailsState.isLoading) {
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

        submitDetailsState.error?.let { errorMessage ->
            ShowToastMessage(errorMessage)
            viewModel.clearErrorMessage()
        }

        if (submitDetailsState.isPdfSaved) {
            val route = viewModel.createPDFScreenRoute(branch = creditBookingData.branch)
            route.let {
                viewModel.clearState()
                navController.navigate(route) {
                    popUpTo(route = Screen.Home.route) {
                        inclusive = false
                    }
                }
            }
        }

        LaunchedEffect(submitDetailsState.isDataSubmitted) {
            if (submitDetailsState.isDataSubmitted) {
                try {
                    val byteArray = viewModel.createPdf(context)
                    val fileName = "${creditBookingData.clientName}_CreditBooking_${System.currentTimeMillis()}.pdf"
                    viewModel.savePdf(byteArray, fileName, branch = creditBookingData.branch)
                } catch (e: Exception) {
                    // Handle Exception
                }
            }
        }
    }

}

