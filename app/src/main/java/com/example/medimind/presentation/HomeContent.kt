package com.example.medimind.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimind.presentation.components.ViewAll
import com.example.medimind.viewmodel.UserViewModel

@Composable
fun HomeContent(
    viewModel: UserViewModel,
    onMenuIconClick: () -> Unit,
    onVIewAllMedicationClick: () -> Unit,
    onViewAllEventsClick: () -> Unit,
    onButtonClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .padding(end = 24.dp, start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onMenuIconClick()
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon")
            }
            Text(text = "MediMind", fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourite Icon")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ViewAll(
                    title = "Current Medications",
                    modifier = Modifier.fillMaxWidth(),
                    onViewALlClick = { onVIewAllMedicationClick() }
                )

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 100.dp)) {
                    Text(text = "Medications", modifier = Modifier.height(48.dp))
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ViewAll(
                    title = "Upcoming Events",
                    modifier = Modifier.fillMaxWidth(),
                    onViewALlClick = { onViewAllEventsClick() }
                )

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 152.dp)
                ) {
                    Text(text = "Events", modifier = Modifier.height(48.dp))
                }
            }
        }


        ElevatedButton(
            onClick = { onButtonClick() },
            shape = RoundedCornerShape(15)
        ) {
            Text(text = "Add New", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}


