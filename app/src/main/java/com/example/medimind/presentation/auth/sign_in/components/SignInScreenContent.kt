package com.example.medimind.presentation.auth.sign_in.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimind.components.CustomOutlinedField
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
            .padding(vertical = 48.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MediMind", fontWeight = FontWeight.Thin, fontSize = 64.sp)
        Spacer(modifier = Modifier.height(48.dp))
        CustomOutlinedField(
            value = uiState.email,
            onValueChange = { email ->
                signInViewModel.onEvent(SignInUIEvent.EmailChanged(email))
            },
            labelValue = "Email",
            errorMessage = uiState.emailError
        )
        CustomOutlinedField(
            value = uiState.password,
            onValueChange = { password ->
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