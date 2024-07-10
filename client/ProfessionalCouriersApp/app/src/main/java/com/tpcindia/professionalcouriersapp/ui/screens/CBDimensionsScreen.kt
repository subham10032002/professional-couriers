package com.tpcindia.professionalcouriersapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.DropdownTextField
import com.tpcindia.professionalcouriersapp.ui.components.InputTextFieldWithSum
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.theme.GradientLeft
import com.tpcindia.professionalcouriersapp.ui.theme.GradientRight
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.CBDimensionsViewModel

@Composable
fun CBDimensionsScreen(
    navController: NavController,
    creditBookingData: CreditBookingData,
    viewModel: CBDimensionsViewModel
) {
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val length by viewModel.length.collectAsState()
    val width by viewModel.width.collectAsState()
    val height by viewModel.height.collectAsState()
    val lengthSum by viewModel.lengthSum.collectAsState()
    val widthSum by viewModel.widthSum.collectAsState()
    val heightSum by viewModel.heightSum.collectAsState()

    val context = LocalContext.current

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
                    append(creditBookingData.noOfPsc)
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
                onValueChange = { viewModel.onLengthChanged(it, creditBookingData.noOfPsc.toInt()) },
                label = "Ex- 10,20,30",
                sum = lengthSum,
                maxEntries = creditBookingData.noOfPsc.toInt()
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Width ", false)
            InputTextFieldWithSum(
                value = width,
                onValueChange = { viewModel.onWidthChanged(it, creditBookingData.noOfPsc.toInt()) },
                label = "Ex- 10,20,30",
                sum = widthSum,
                maxEntries = creditBookingData.noOfPsc.toInt()
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Height ", false)
            InputTextFieldWithSum(
                value = height,
                onValueChange = { viewModel.onHeightChanged(it, creditBookingData.noOfPsc.toInt()) },
                label = "Ex- 10,20,30",
                sum = heightSum,
                maxEntries = creditBookingData.noOfPsc.toInt()
            )
        }

        // Bottom buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CustomButton(
                onClick = {
                    navController.navigate(viewModel.createCBInfoRoute(CBDimensionData()))
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
                    if (viewModel.validateEntries(length, width, height, creditBookingData.noOfPsc.toInt())) {
                        navController.navigate(viewModel.createCBInfoRoute())
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
                backgroundColor = Red,
            )
        }
    }
}