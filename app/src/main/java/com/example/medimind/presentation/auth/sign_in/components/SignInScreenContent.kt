package com.example.medimind.presentation.auth.sign_in.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.medimind.R
import com.example.medimind.components.CustomOutlinedField
import com.example.medimind.components.CustomPasswordField
import com.example.medimind.domain.events.SignInUIEvent
import com.example.medimind.presentation.auth.sign_in.SignInViewModel

@Composable
fun SignInScreenContent(
    signInViewModel: SignInViewModel,
    navigateToSignUpPage: () -> Unit
) {

    val uiState = signInViewModel.signInUIState.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.medi_mind_black),
                contentDescription = "medi_mind_logo",
                modifier = Modifier.size(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            CustomOutlinedField(
                value = uiState.email,
                onValueChange = { email ->
                    signInViewModel.onEvent(SignInUIEvent.EmailChanged(email))
                },
                labelValue = "Email",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                errorMessage = uiState.emailError
            )
            CustomPasswordField(
                value = uiState.password,
                onPasswordChange = { password ->
                    signInViewModel.onEvent(SignInUIEvent.PasswordChanged(password))
                },
                labelValue = "Password",
                errorMessage = uiState.passwordError
            )
            Spacer(modifier = Modifier.height(48.dp))
            ElevatedButton(
                onClick = {
                    signInViewModel.onEvent(SignInUIEvent.SignInButtonClicked)
                },
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(text = "Sign In")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "New in MediMind?")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign Up",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { navigateToSignUpPage() },
                    textDecoration = TextDecoration.Underline
                )
            }
        }

    }
}