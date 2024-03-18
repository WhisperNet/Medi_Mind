package com.example.medimind.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.viewmodel.UserViewModel

@Composable
fun MedicationListScreen(
    onIconClick: () -> Unit,
    onButtonClick: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    val userState by viewModel.userState.collectAsState()
    if (userState.data != null) {
        viewModel.getMedications(userState.data!!.email)
    }
    val medicationList by viewModel.medicationItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onIconClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Navigate Back Icon",
                    modifier = Modifier.rotate(90f)
                )
            }
            Text(text = "Medication List", fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Default.Medication, contentDescription = "Medication Icon")
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(560.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = medicationList
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(top = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HealthAndSafety,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = it.name, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(Color.Black)
                                    .clickable {

                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Restock",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Alarm,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = it.time, fontWeight = FontWeight.SemiBold)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Stock: ${it.stock}")
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.RemoveCircleOutline,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    if (it.stock != 0) {
                                        userState.data?.let { user -> viewModel.updateMedicationStock(it.name, user.email, it.stock -1) }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(48.dp))

        ElevatedButton(
            onClick = onButtonClick,
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = "Add New",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
//    MedicationListScreen(
//        onIconClick = { /*TODO*/ },
//        onButtonClick = { /*TODO*/ },
////        viewModel = UserViewModel(
////            firestoreRef = Firebase.firestore,
////            auth = Firebase.auth
////        )
//    )
}