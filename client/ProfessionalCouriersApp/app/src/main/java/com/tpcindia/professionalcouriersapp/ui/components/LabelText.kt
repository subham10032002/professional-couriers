package com.tpcindia.professionalcouriersapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun LabelText(text: String, showAsterisk: Boolean = true) {
    Text(
        text = buildAnnotatedString {
            append(text)
            if (showAsterisk) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }
        },
        color = Color.Gray,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
    )
}