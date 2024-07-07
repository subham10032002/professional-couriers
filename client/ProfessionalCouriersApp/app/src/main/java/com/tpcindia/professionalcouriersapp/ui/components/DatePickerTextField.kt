package com.tpcindia.professionalcouriersapp.ui.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*
import com.tpcindia.professionalcouriersapp.R

@Composable
fun DatePickerInputField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var dateText by remember { mutableStateOf(selectedDate) }

    OutlinedTextField(
        value = dateText,
        onValueChange = {
            // Prevent the user from directly typing in the field
            if (it == dateText) {
                showDatePicker(context) { date ->
                    dateText = date
                    onDateSelected(date)
                }
            }
        },
        placeholder = { Text(label, color = Color.LightGray) },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.tpc_calendar),
                contentDescription = "Calendar Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            showDatePicker(context) { date ->
                                dateText = date
                                onDateSelected(date)
                            }
                        }
                    }
                }
            },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}

private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        onDateSelected(date)
    }, year, month, day).show()
}

@Preview
@Composable
fun DatePickerInputFieldPreview() {
    DatePickerInputField(
        label = "Select Date",
        selectedDate = "selectedDate",
        onDateSelected = { var selectedDate = it }
    )
}

