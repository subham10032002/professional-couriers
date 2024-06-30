package com.tpcindia.professionalcouriersapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpcindia.professionalcouriersapp.ui.theme.GradientLeft
import com.tpcindia.professionalcouriersapp.ui.theme.GradientRight

@Composable
fun InputTextFieldWithSum(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    var sum by remember { mutableStateOf(0) }

    LaunchedEffect(value) {
        sum = value.split(",")
            .filter { it.isNotEmpty() }
            .mapNotNull { it.toIntOrNull() }
            .sum()
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = Color.LightGray
            )
        },
        trailingIcon = {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp),
                border = BorderStroke(
                    1.dp, Brush.horizontalGradient(
                        colors = listOf(GradientLeft, GradientRight)
                    )
                ),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent),
                ) {
                    Text(text = sum.toString(), color = Color.Black)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(46.dp)
    )
}

@Preview
@Composable
fun InputTextFieldWithSumPreview() {
    InputTextFieldWithSum(
        value = "10,20,30,40",
        onValueChange = { },
        label = "Enter numbers separated by commas"
    )
}
