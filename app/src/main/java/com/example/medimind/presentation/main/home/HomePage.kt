package com.example.medimind.presentation.main.home

import androidx.compose.runtime.Composable
import com.example.medimind.presentation.SharedViewModel
import com.example.medimind.presentation.main.home.components.HomeContent

@Composable
fun HomePage(
    sharedViewModel: SharedViewModel,
    onViewAllMedicationClick: () -> Unit,
    onViewAllEventClick: () -> Unit,
    onButtonClick: () -> Unit,
) {

    sharedViewModel.getMedications()
    sharedViewModel.getEvents()

    HomeContent(
        sharedViewModel = sharedViewModel,
        onVIewAllMedicationClick = onViewAllMedicationClick,
        onViewAllEventsClick = onViewAllEventClick,
        onButtonClick = onButtonClick
    )

}