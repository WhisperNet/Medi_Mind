package com.example.medimind.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.viewmodel.UserViewModel
import com.example.medimind.domain.model.Response

@Composable
//@Preview(showSystemUi = true, showBackground = true)
fun HomePage(
    viewModel: UserViewModel = hiltViewModel(),
    onMenuIconClick: () -> Unit,
    onViewAllMedicationClick: () -> Unit,
    onViewAllEventClick: () -> Unit,
    onButtonClick: () -> Unit,
) {

    val uiState by viewModel.userState.collectAsState()

    when (uiState) {
        is Response.Failure -> Text(text = "Failure")
        Response.Loading -> Text(text = "Loading")
        is Response.Success -> {
            uiState.data?.let {
                viewModel.getMedications(it.email)
                viewModel.getEvents(it.email)
                HomeContent(
                    viewModel,
                    onMenuIconClick = { onMenuIconClick() },
                    onVIewAllMedicationClick = { onViewAllMedicationClick() },
                    onViewAllEventsClick = { onViewAllEventClick() },
                    onButtonClick = { onButtonClick() }
                )
            }
        }
    }

}