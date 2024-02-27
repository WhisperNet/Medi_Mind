package com.example.medimind.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medimind.presentation.HomePage
import androidx.navigation.NavController
import com.example.medimind.presentation.SignInPage
import com.example.medimind.presentation.SignUpPage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MediMindApp() {

    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = if(Firebase.auth.currentUser != null) { Routes.Home.name } else { Routes.SignIn.name }) {

        composable(route = Routes.SignIn.name) {
            SignInPage(
                navigateToHomePage = { navigateTo(navController, Routes.Home.name) },
                navigateToSignUpPage = { navigateTo(navController, Routes.SignUp.name) }
            )
        }

        composable(route = Routes.Home.name) {
            HomePage()
        }

        composable(route = Routes.SignUp.name) {
            SignUpPage(
                navigateToHomePage = {
                    navigateTo(navController, Routes.Home.name)
                }
            )
        }
    }
}




private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route = route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}