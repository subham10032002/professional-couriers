package com.tpcindia.professionalcouriersapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 60.dp,
    isFilled: Boolean = true,
    loginState: Boolean = false,
    gradientColors: List<Color>? = null,
    text: String,
    textColor: Color,
    backgroundColor: Color
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (isFilled) backgroundColor else Color.LightGray),
        shape = RoundedCornerShape(23.dp),
        enabled = isFilled,
        border = if (gradientColors != null) {
            BorderStroke(
                width = 1.dp,
                brush = Brush.horizontalGradient(gradientColors)
            )
        } else null
    ) {
        if (loginState) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}