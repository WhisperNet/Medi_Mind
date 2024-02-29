package com.example.medimind.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimind.presentation.components.DropDownField
import com.example.medimind.presentation.components.Field

@Composable
fun AddNewReminderPage(onSaveClick: () -> Unit) {

    val types = listOf("Medication", "Event", "Vaccination")

    var type by rememberSaveable { mutableStateOf(types[0]) }

    var name by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var stock by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
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

        Icon(
            imageVector = Icons.Filled.AccessAlarms,
            contentDescription = "Alarm Icon",
            modifier = Modifier
                .clip(CircleShape)
                .size(72.dp)
                .padding(4.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropDownField(itemsList = types, selectedItem = type, onItemChange = {type = it}, title = "Type")
            Field(title = "Name", value = name, onValueChange = {name = it}, modifier = Modifier.fillMaxWidth())
            Field(title = "Date", value = date, onValueChange = {date = it}, modifier = Modifier.fillMaxWidth())
            Field(title = "Time", value = time, onValueChange = {time = it}, modifier = Modifier.fillMaxWidth())
            Field(title = "Stock", value = stock, onValueChange = {stock = it}, modifier = Modifier.fillMaxWidth())
        }

        ElevatedButton(
            onClick = onSaveClick,
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.White)
        }
    }
}