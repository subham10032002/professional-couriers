package com.tpcindia.professionalcouriersapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tpcindia.professionalcouriersapp.ui.theme.GradientLeft
import com.tpcindia.professionalcouriersapp.ui.theme.GradientRight

@Composable
fun InputTextFieldWithSum(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    maxEntries: Int,
    keyboardType: KeyboardType = KeyboardType.Number,
    sum: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val commaCount = newValue.count { char -> char == ',' }
            if (commaCount < maxEntries) {
                onValueChange(newValue)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        placeholder = { Text(label, color = Color.LightGray) },
        trailingIcon = {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .size(60.dp),
                border = BorderStroke(
                    1.dp, Brush.horizontalGradient(
                        colors = listOf(GradientLeft, GradientRight)
                    )
                ),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                ) {
                    Text(
                        text = sum.toString(),
                        color = Color.Black,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(1.dp)
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
    )
}
