package com.example.medimind.presentation.auth.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.presentation.auth.sign_in.components.SignInScreenContent
import com.example.medimind.presentation.components.LoadingScreen

@Composable
fun SignInPage(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navigateToHomePage: () -> Unit,
    navigateToSignUpPage: () -> Unit
) {

    val navigationState = signInViewModel.navigationState.collectAsState().value
    val loadingState = signInViewModel.loadingState.collectAsState().value

    if (navigationState) {
        navigateToHomePage.invoke()
    } else if (loadingState) {
        LoadingScreen()
    } else {
        SignInScreenContent(
            signInViewModel = signInViewModel,
            navigateToSignUpPage = navigateToSignUpPage
        )
    }
}