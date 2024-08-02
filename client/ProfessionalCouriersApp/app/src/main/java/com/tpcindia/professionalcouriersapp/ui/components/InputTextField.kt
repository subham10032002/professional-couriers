package com.tpcindia.professionalcouriersapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = if (maxLength != null && value.length > maxLength) {
            value.substring(0, maxLength)
        } else {
            value
        },
        readOnly = if (readOnly) true else false,
        onValueChange = { newValue ->
            if (maxLength == null || newValue.length <= maxLength) {
                onValueChange(newValue)
            }
        },
        placeholder = { Text(label, color = Color.LightGray) },
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}
