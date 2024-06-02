package com.example.medimind.presentation.auth.sign_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimind.components.CustomOutlinedFieldWithPlaceholder
import com.example.medimind.domain.events.SignUpUIEvent
import com.example.medimind.presentation.auth.sign_up.SignUpViewModel
import com.example.medimind.strings.OTP_DESCRIPTION

@Composable
fun VerifyOTPScreen(
    signUpViewModel: SignUpViewModel,
) {

    val signUpUIState = signUpViewModel.signUpUIState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "MediMind", fontWeight = FontWeight.Thin, fontSize = 64.sp)
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = OTP_DESCRIPTION,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedFieldWithPlaceholder(
            value = signUpUIState.otp,
            labelValue = "OTP",
            placeholderValue = "* * * * * *",
            onValueChange = { otp ->
                signUpViewModel.onEvent(SignUpUIEvent.OTPChanged(otp))
            },
            errorMessage = signUpUIState.otpError
        )
        Spacer(modifier = Modifier.height(24.dp))
        ElevatedButton(
            onClick = {
                signUpViewModel.onEvent(SignUpUIEvent.VerifyOTPButtonClicked)
            },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(text = "Verify OTP")
        }

    }
}


