package com.example.medimind.presentation.main.add_reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimind.domain.events.AddNewReminderUIEvent
import com.example.medimind.presentation.SharedViewModel
import com.example.medimind.presentation.components.DropDownField
import com.example.medimind.presentation.components.Field
import com.example.medimind.util.Constants.ALL_EVENTS
import com.example.medimind.util.Constants.MEDICATION_EVENTS

@Composable
fun AddNewReminderScreen(
    sharedViewModel: SharedViewModel,
    navigateBack: () -> Unit,
) {

    val uiState = sharedViewModel.addNewReminderUIState.collectAsState().value
    val eventFrom = sharedViewModel.eventFrom.collectAsState().value
    val navigateBackState = sharedViewModel.navigateBackState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Add New Reminders",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp
        )

        ElevatedCard(
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(5.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Icon(
                imageVector = Icons.Filled.AccessAlarms,
                contentDescription = "Alarm Icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(72.dp)
                    .padding(8.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (eventFrom != 2) {
                DropDownField(
                    itemsList = if (eventFrom == 1) {
                        MEDICATION_EVENTS
                    } else {
                        ALL_EVENTS
                    },
                    selectedItem = uiState.type,
                    onItemChange = { type ->
                        sharedViewModel.onAddNewReminderUIEvent(
                            AddNewReminderUIEvent.TypeChanged(
                                type
                            )
                        )
                    },
                    title = "Type"
                )
            }
            Field(
                title = "Name",
                value = uiState.name,
                onValueChange = { name ->
                    sharedViewModel.onAddNewReminderUIEvent(AddNewReminderUIEvent.NameChanged(name))
                },
                modifier = Modifier.fillMaxWidth(),
                errorMessage = uiState.nameError
            )
            Field(
                title = "Date",
                value = uiState.date,
                onValueChange = { date ->
                    sharedViewModel.onAddNewReminderUIEvent(AddNewReminderUIEvent.DateChanged(date))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
                errorMessage = uiState.dateError
            )
            Field(
                title = "Time",
                value = uiState.time,
                onValueChange = { time ->
                    sharedViewModel.onAddNewReminderUIEvent(AddNewReminderUIEvent.TimeChanged(time))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier.fillMaxWidth(),
                errorMessage = uiState.timeError
            )
            Field(
                title = "Stock",
                value = uiState.stock,
                onValueChange = { stock ->
                    sharedViewModel.onAddNewReminderUIEvent(AddNewReminderUIEvent.StockChanged(stock))
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                errorMessage = uiState.stockError
            )
        }

        ElevatedButton(
            onClick = {
                sharedViewModel.onAddNewReminderUIEvent(AddNewReminderUIEvent.SaveButtonClicked)
                if (navigateBackState) {
                    navigateBack()
                }
            },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Save",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }

        ElevatedButton(
            onClick = {
                sharedViewModel.onAddNewReminderUIEvent(AddNewReminderUIEvent.GoBackButtonClicked)
                navigateBack()
            },
            shape = RoundedCornerShape(15),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Go Back",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )
        }
    }
}