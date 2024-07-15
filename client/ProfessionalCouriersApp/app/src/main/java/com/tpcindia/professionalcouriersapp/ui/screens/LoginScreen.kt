package com.tpcindia.professionalcouriersapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.ui.components.CustomButton
import com.tpcindia.professionalcouriersapp.ui.navigation.Screen
import com.tpcindia.professionalcouriersapp.ui.theme.Red
import com.tpcindia.professionalcouriersapp.viewModel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val isFilled = username.isNotEmpty() && password.isNotEmpty()
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState.error != null) {
            Toast.makeText(context, loginState.error, Toast.LENGTH_SHORT).show()
        }
        if (loginState.isAuthenticated) {
            val route = viewModel.createHomeScreenRoute()
            if (route != null) {
                navController.navigate(route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(id = R.drawable.tpc_postman_image),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .width(150.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.tpc_logo),
            contentDescription = null,
            modifier = Modifier
                .height(65.dp)
                .width(270.dp)
        )
        Spacer(modifier = Modifier.height(65.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username", color = Color.LightGray) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .height(56.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            shape = RoundedCornerShape(7.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", color = Color.LightGray) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .height(56.dp),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.tpc_open_eye)
                else
                    painterResource(id = R.drawable.tpc_password_visibility_off)

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }, modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        painter = image,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            shape = RoundedCornerShape(7.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(40.dp))

        CustomButton(
            onClick = {
                viewModel.login(username, password)
                      },
            horizontalPadding = 60.dp,
            isFilled = isFilled,
            loginState = loginState.isLoading,
            text = "LogIn",
            textColor = Color.White,
            backgroundColor = Red
        )
    }
}
