package com.tpcindia.professionalcouriersapp.ui.screens

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.configs.UIConfig
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.DropdownTextField
import com.tpcindia.professionalcouriersapp.ui.components.InputTextFieldWithSum
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.ShowToastMessage
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.theme.GradientLeft
import com.tpcindia.professionalcouriersapp.ui.theme.GradientRight
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.CBDimensionsViewModel
import com.tpcindia.professionalcouriersapp.viewModel.SharedViewModel

@Composable
fun CBDimensionsScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: CBDimensionsViewModel
) {
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val length by viewModel.length.collectAsState()
    val width by viewModel.width.collectAsState()
    val height by viewModel.height.collectAsState()
    val lengthSum by viewModel.lengthSum.collectAsState()
    val widthSum by viewModel.widthSum.collectAsState()
    val heightSum by viewModel.heightSum.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val creditBookingData by sharedViewModel.creditBookingData.collectAsState()

    val noOfPsc = creditBookingData.noOfPsc.toIntOrNull() ?: 0

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = buildAnnotatedString {
                    append("No of psc selected was: ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append(if (noOfPsc == 0) "0" else creditBookingData.noOfPsc)
                    }
                },
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Scrollable input fields
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                LabelText("Unit ")
                DropdownTextField(
                    label = "Select..",
                    options = UIConfig.UNIT,
                    selectedOption = selectedUnit,
                    onOptionSelected = { viewModel.onUnitSelected(it) }
                )

                Spacer(modifier = Modifier.height(15.dp))

                LabelText("Length ", false)
                InputTextFieldWithSum(
                    value = length,
                    onValueChange = { viewModel.onLengthChanged(it, noOfPsc) },
                    label = "Ex- 10,20,30",
                    sum = lengthSum,
                    maxEntries = noOfPsc
                )

                Spacer(modifier = Modifier.height(15.dp))

                LabelText("Width ", false)
                InputTextFieldWithSum(
                    value = width,
                    onValueChange = { viewModel.onWidthChanged(it, noOfPsc) },
                    label = "Ex- 10,20,30",
                    sum = widthSum,
                    maxEntries = noOfPsc
                )

                Spacer(modifier = Modifier.height(15.dp))

                LabelText("Height ", false)
                InputTextFieldWithSum(
                    value = height,
                    onValueChange = { viewModel.onHeightChanged(it, noOfPsc) },
                    label = "Ex- 10,20,30",
                    sum = heightSum,
                    maxEntries = noOfPsc
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CustomButton(
                    onClick = {
                        viewModel.clearState()
                        sharedViewModel.setCreditBookingData(creditBookingData)
                        navController.navigate(viewModel.createCBInfoRoute(sharedViewModel))
                    },
                    isFilled = true,
                    horizontalPadding = 0.dp,
                    modifier = Modifier.weight(1f),
                    loginState = false,
                    text = "Skip",
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    gradientColors = mutableListOf(GradientLeft, GradientRight)
                )

                Spacer(modifier = Modifier.width(16.dp))

                CustomButton(
                    onClick = {
                        if (viewModel.validateEntries(length, width, height, noOfPsc)) {
                            viewModel.clearState()
                            sharedViewModel.setCreditBookingData(creditBookingData)
                            navController.navigate(viewModel.createCBInfoRoute(sharedViewModel))
                        } else {
                            Toast.makeText(context, "Please enter exactly ${creditBookingData.noOfPsc} numbers in each field.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    horizontalPadding = 0.dp,
                    isFilled = true,
                    modifier = Modifier.weight(1f),
                    loginState = false,
                    text = "Next",
                    textColor = Color.White,
                    backgroundColor = Red
                )
            }
        }


        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
                    .clickable(enabled = false, onClick = { /* Do nothing */ })
            ) {
                CircularProgressIndicator(color = Color.White)
                Text(
                    text = "Please wait, we're submitting the details...",
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        error?.let { errorMessage ->
            ShowToastMessage(errorMessage)
            viewModel.clearErrorMessage()
        }
    }
}