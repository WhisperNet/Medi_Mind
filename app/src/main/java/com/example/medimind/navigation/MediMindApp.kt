package com.example.medimind.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medimind.presentation.main.home.HomePage
import androidx.navigation.NavController
import com.example.medimind.presentation.SharedViewModel
import com.example.medimind.presentation.main.add_reminder.AddNewReminderScreen
import com.example.medimind.presentation.main.medication.MedicationListScreen
import com.example.medimind.presentation.auth.sign_in.SignInPage
import com.example.medimind.presentation.auth.sign_up.SignUpPage
import com.example.medimind.presentation.main.event.UpcomingEventsScreen
import com.example.medimind.util.Constants.TAG

@Composable
fun MediMindApp() {

    val sharedViewModel: SharedViewModel = hiltViewModel()
    val user = sharedViewModel.user.collectAsState().value
    Log.d(TAG, "MediMindApp: $user")

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (user.email.isNotBlank()) {
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
                },
                navigateToSignInPage = {
                    navigateTo(navController, Routes.SignIn.name)
                }
            )
        }

        composable(route = Routes.Home.name) {
            HomePage(
                sharedViewModel = sharedViewModel,
                onViewAllMedicationClick = {
                    navigateTo(
                        navController,
                        Routes.MeditationList.name
                    )
                },
                onViewAllEventClick = { navigateTo(navController, Routes.UpcomingEvents.name) },
                onButtonClick = { navigateWithoutPop(navController, Routes.AddNew.name) }
            )
        }

        composable(route = Routes.MeditationList.name) {
            MedicationListScreen(
                sharedViewModel = sharedViewModel,
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
                sharedViewModel = sharedViewModel,
                onNavigateBackIconClick = {
                    navigateTo(navController, Routes.Home.name)
                },
                onAddNewButtonClick = {
                    navigateWithoutPop(navController, Routes.AddNew.name)
                }
            )
        }

        composable(route = Routes.AddNew.name) {
            AddNewReminderScreen(
                sharedViewModel = sharedViewModel,
                navigateBack = {
                    navigateBack(navController)
                },
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

private fun navigateBack(navController: NavController) {
    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navController.popBackStack()
    }
}