package com.example.medimind.presentation.auth.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.presentation.auth.sign_up.components.SignUpScreenContent
import com.example.medimind.presentation.auth.sign_up.components.VerifyOTPScreen
import com.example.medimind.presentation.components.LoadingScreen

@Composable
fun SignUpPage(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navigateToHomePage: () -> Unit,
    navigateToSignInPage: () -> Unit
) {
    val navigationState = signUpViewModel.navigationState.collectAsState().value
    val loadingState = signUpViewModel.loadingState.collectAsState().value
    val otpScreenState = signUpViewModel.otpScreenState.collectAsState().value

    if (navigationState) {
        navigateToHomePage()
    }else if (loadingState) {
        LoadingScreen()
    }else if (otpScreenState) {
        VerifyOTPScreen(signUpViewModel = signUpViewModel)
    } else {
        SignUpScreenContent(
            signUpViewModel = signUpViewModel,
            navigateToSignInPage = navigateToSignInPage
        )
    }

}