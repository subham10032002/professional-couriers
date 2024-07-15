package com.tpcindia.professionalcouriersapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.ui.theme.GradientLeft
import com.tpcindia.professionalcouriersapp.ui.theme.GradientRight

@Composable
fun TopBanner(showMenuIcon: Boolean = false, onPDFsClick: () -> Unit = {}) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(GradientLeft, GradientRight)
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tpc_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(42.dp)
                .width(166.dp)
                .align(Alignment.Center)
        )

        if (showMenuIcon) {
            Box(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tpc_menu),
                        contentDescription = null,
                        tint = Color.White // Icon color
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            onPDFsClick()
                        },
                        text = { Text("PDFs") }
                    )
                }
            }
        }
    }
}
