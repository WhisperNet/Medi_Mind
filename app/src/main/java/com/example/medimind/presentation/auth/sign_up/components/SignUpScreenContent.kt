package com.example.medimind.presentation.auth.sign_up.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.medimind.domain.events.SignUpUIEvent
import com.example.medimind.presentation.auth.sign_up.SignUpViewModel

@Composable
fun SignUpScreenContent(
    signUpViewModel: SignUpViewModel,
    navigateToSignInPage: () -> Unit
) {
    val uiState = signUpViewModel.signUpUIState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "MediMind", fontWeight = FontWeight.Thin, fontSize = 64.sp)
        Spacer(modifier = Modifier.height(48.dp))

        CustomOutlinedField(
            value = uiState.name,
            onValueChange = { name ->
                signUpViewModel.onEvent(SignUpUIEvent.NameChanged(name))
            },
            labelValue = "Name",
        )
        CustomOutlinedField(
            value = uiState.address,
            onValueChange = { address ->
                signUpViewModel.onEvent(SignUpUIEvent.AddressChanged(address))
            },
            labelValue = "Address",
        )
        CustomOutlinedField(
            value = uiState.phoneNo,
            onValueChange = { phoneNo ->
                signUpViewModel.onEvent(SignUpUIEvent.PhoneNoChanged(phoneNo))
            },
            labelValue = "Phone No",
        )
        CustomOutlinedField(
            value = uiState.email,
            onValueChange = { email ->
                signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(email))
            },
            labelValue = "Email",
        )
        CustomOutlinedField(
            value = uiState.password,
            onValueChange = { password ->
                signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(password))
            },
            labelValue = "Password",
        )

        Spacer(modifier = Modifier.height(48.dp))
        ElevatedButton(
            onClick = {
                signUpViewModel.onEvent(SignUpUIEvent.SignUpButtonClicked)
            },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(text = "Sign Up")
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Already have an  account?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign In",
                    modifier = Modifier.padding(vertical =  8.dp).clickable { navigateToSignInPage() },
                textDecoration = TextDecoration.Underline
            )
        }
    }
}