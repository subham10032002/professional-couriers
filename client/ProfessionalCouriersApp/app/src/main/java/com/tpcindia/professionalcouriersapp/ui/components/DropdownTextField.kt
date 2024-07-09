package com.tpcindia.professionalcouriersapp.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropdownTextField(
    label: String,
    options: List<String>,
    selectedOption: String,
    modifier: Modifier = Modifier,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var filteredOptions by remember { mutableStateOf(options) }
    var input by remember { mutableStateOf(selectedOption) }

    Column {
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                filteredOptions = options.filter { option ->
                    option.contains(it, ignoreCase = true)
                }
                expanded = true
            },
            placeholder = { Text(label, color = Color.LightGray) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        input = option
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option) } // Provide content in a trailing lambda
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownTextFieldPreview() {
    DropdownTextField(
        label = "Select..",
        options = mutableListOf("ab", "abcd"),
        selectedOption = "selectedClientName",
        onOptionSelected = { var selectedClientName = it }
    )
}
