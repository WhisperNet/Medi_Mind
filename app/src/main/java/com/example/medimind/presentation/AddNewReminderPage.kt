package com.example.medimind.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.data.model.Event
import com.example.medimind.presentation.components.DropDownField
import com.example.medimind.presentation.components.Field
import com.example.medimind.viewmodel.UserViewModel

@Composable
fun AddNewReminderPage(
    onSaveClick: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel()
) {

    val types = listOf("Medication", "Event", "Vaccination")

    var type by rememberSaveable { mutableStateOf(types[0]) }

    var name by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var stock by rememberSaveable { mutableStateOf("") }

    val userState by userViewModel.userState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
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
            DropDownField(
                itemsList = types,
                selectedItem = type,
                onItemChange = { type = it },
                title = "Type"
            )
            Field(
                title = "Name",
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth()
            )
            Field(
                title = "Date",
                value = date,
                onValueChange = { date = it },
                modifier = Modifier.fillMaxWidth()
            )
            Field(
                title = "Time",
                value = time,
                onValueChange = { time = it },
                modifier = Modifier.fillMaxWidth()
            )
            Field(
                title = "Stock",
                value = stock,
                onValueChange = { stock = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        ElevatedButton(
            onClick = {
                val currentStock = if (stock.isBlank()) { 0 } else stock.toInt()
                val event = Event(
                    type = type,
                    name = name,
                    date = date,
                    time = time,
                    stock = currentStock
                )
                if (userState.data != null && type.isNotBlank() && name.isNotBlank() && date.isNotBlank() && time.isNotBlank()) {
                    userViewModel.setEvent(event = event, email = userState.data!!.email)
                }
                onSaveClick()
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
    }
}


@Preview(showSystemUi = true)
@Composable
fun AddNewPreview() {
//    AddNewReminderPage {
//
//    }
}