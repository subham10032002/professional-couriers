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
import androidx.compose.ui.text.input.KeyboardType
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
    allowDecimals: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = if (maxLength != null && value.length > maxLength) {
            value.substring(0, maxLength)
        } else {
            value
        },
        readOnly = readOnly,
        onValueChange = { newValue ->
            if (allowDecimals != null) {
                // This is use to check if the value contains decimals or not
                // and if it contains decimal then the length of the decimals should be in one range
                // also the value should contain only one decimal
                val substringValue = newValue.substringAfter(".")
                if ((substringValue.length <= allowDecimals && newValue.count { it == '.' } <= 1)
                    || !newValue.contains(".")) {
                    onValueChange(newValue)
                }
                return@OutlinedTextField
            }

            // This is to check if the value contains only numbers and if it is under some range of length
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
