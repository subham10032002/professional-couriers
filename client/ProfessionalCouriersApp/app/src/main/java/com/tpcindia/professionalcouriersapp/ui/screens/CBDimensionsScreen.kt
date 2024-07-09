package com.tpcindia.professionalcouriersapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.configs.UIConfig
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.components.DropdownTextField
import com.tpcindia.professionalcouriersapp.ui.components.InputTextFieldWithSum
import com.tpcindia.professionalcouriersapp.ui.components.LabelText
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.ui.theme.GradientLeft
import com.tpcindia.professionalcouriersapp.ui.theme.GradientRight
import com.tpcindia.professionalcouriersapp.ui.theme.Red

@Composable
fun CBDimensionsScreen(creditBookingData: CreditBookingData) {

    var selectedUnit by remember { mutableStateOf("") }

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
                onOptionSelected = { selectedUnit = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Length ", false)
            InputTextFieldWithSum(
                value = "",
                onValueChange = { },
                label = "Ex- 10,20,30"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Width ", false)
            InputTextFieldWithSum(
                value = "",
                onValueChange = { },
                label = "Ex- 10,20,30"
            )

            Spacer(modifier = Modifier.height(15.dp))

            LabelText("Height ", false)
            InputTextFieldWithSum(
                value = "",
                onValueChange = { },
                label = "Ex- 10,20,30"
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
                onClick = {  },
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
                onClick = {  },
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

