package com.example.medimind.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.components.ErrorScreen
import com.example.medimind.components.LoadingScreen
import com.example.medimind.data.model.User
import com.example.medimind.domain.model.Response
import com.example.medimind.viewmodel.AuthenticationViewModel

@Composable
fun SignUpPage(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navigateToHomePage: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phoneNo by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is Response.Failure -> {
            ErrorScreen(message = uiState.error.toString())
        }
        Response.Loading -> {
            LoadingScreen()
        }
        is Response.Success -> {
            if (uiState.data == true) {
                navigateToHomePage.invoke()
            } else {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(value = name, onValueChange = {name = it}, label = { Text(text = "Name")}, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = address, onValueChange = {address = it}, label = { Text(text = "Address")}, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phoneNo, onValueChange = {phoneNo = it}, label = { Text(text = "Phone No")}, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text(text = "Email")}, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text(text = "Password")}, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(48.dp))
                    ElevatedButton(onClick = {
                        val user = User(name, address, phoneNo, email)
                        viewModel.signUp(email, password, user)
                    }) {
                        Text(text = "Sign Up")
                    }
                }
            }
        }
    }

    

}