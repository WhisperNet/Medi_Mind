package com.example.medimind.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medimind.presentation.HomePage
import androidx.navigation.NavController
import com.example.medimind.presentation.AddNewReminderPage
import com.example.medimind.presentation.MedicationListScreen
import com.example.medimind.presentation.SignInPage
import com.example.medimind.presentation.SignUpPage
import com.example.medimind.presentation.UpcomingEventsScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MediMindApp() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (Firebase.auth.currentUser != null) {
            Routes.Home.name
        } else {
            Routes.SignIn.name
        }
    ) {

        composable(route = Routes.SignIn.name) {
            SignInPage(
                navigateToHomePage = { navigateTo(navController, Routes.Home.name) },
                navigateToSignUpPage = { navigateTo(navController, Routes.SignUp.name) }
            )
        }

        composable(route = Routes.SignUp.name) {
            SignUpPage(
                navigateToHomePage = {
                    navigateTo(navController, Routes.Home.name)
                }
            )
        }

        composable(route = Routes.Home.name) {
            HomePage(
                onMenuIconClick = { /*TODO*/ },
                onViewAllMedicationClick = { navigateTo(navController, Routes.MeditationList.name) },
                onViewAllEventClick = { navigateTo(navController, Routes.UpcomingEvents.name) },
                onButtonClick = { navigateWithoutPop(navController, Routes.AddNew.name) }
            )
        }

        composable(route = Routes.MeditationList.name) {
            MedicationListScreen(
                onIconClick = {
                    navigateTo(navController, Routes.Home.name)
                },
                onButtonClick = {
                    navigateWithoutPop(navController, Routes.AddNew.name)
                }
            )
        }

        composable(route = Routes.UpcomingEvents.name) {
            UpcomingEventsScreen(
                onIconClick = {
                    navigateTo(navController, Routes.Home.name)
                },
                onButtonClick = {
                    navigateWithoutPop(navController, Routes.AddNew.name)
                }
            )
        }

        composable(route = Routes.AddNew.name) {
            AddNewReminderPage(
                onSaveClick = {
                    navController.popBackStack()
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


private fun navigateWithoutPop(navController: NavController, route: String) {
    navController.navigate(route)
}