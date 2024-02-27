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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.data.User
import com.example.medimind.viewmodel.AuthenticationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInPage(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navigateToHomePage: () -> Unit,
    navigateToSignUpPage: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text(text = "Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text(text = "Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(48.dp))
        ElevatedButton(onClick = {
            viewModel.signIn(email, password)
            navigateToHomePage.invoke()
        }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.height(48.dp))
        TextButton(onClick = { navigateToSignUpPage.invoke() }) {
            Text(text = "New User? Sign Up", modifier = Modifier.padding(16.dp))
        }
    }
}