package com.example.medimind.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medimind.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medimind.data.Response

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomePage(
    viewModel: UserViewModel = hiltViewModel()
) {

    val uiState by viewModel.userState.collectAsState()

    when(uiState) {
        is Response.Failure -> Text(text = "Failure")
        Response.Loading -> Text(text = "Loading")
        is Response.Success -> HomeContent()
    }

}