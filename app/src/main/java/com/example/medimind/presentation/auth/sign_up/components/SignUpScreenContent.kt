package com.example.medimind.presentation.auth.sign_up.components

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


        Box(
            modifier = Modifier
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.medi_mind_black),
                contentDescription = "medi_mind_logo",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomOutlinedField(
                value = uiState.name,
                onValueChange = { name ->
                    signUpViewModel.onEvent(SignUpUIEvent.NameChanged(name))
                },
                labelValue = "Name",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                errorMessage = uiState.nameError
            )
            CustomOutlinedField(
                value = uiState.address,
                onValueChange = { address ->
                    signUpViewModel.onEvent(SignUpUIEvent.AddressChanged(address))
                },
                labelValue = "Address",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                errorMessage = uiState.addressError
            )
            CustomOutlinedField(
                value = uiState.phoneNo,
                onValueChange = { phoneNo ->
                    signUpViewModel.onEvent(SignUpUIEvent.PhoneNoChanged(phoneNo))
                },
                labelValue = "Phone No",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                errorMessage = uiState.phoneNoError
            )
            CustomOutlinedField(
                value = uiState.email,
                onValueChange = { email ->
                    signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(email))
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
                    signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(password))
                },
                labelValue = "Password",
                errorMessage = uiState.passwordError
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Already have an  account?")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign In",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { navigateToSignInPage() },
                    textDecoration = TextDecoration.Underline
                )
            }
        }

    }
}