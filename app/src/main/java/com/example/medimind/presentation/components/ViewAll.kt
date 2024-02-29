package com.example.medimind.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ViewAll(
    title: String,
    modifier: Modifier,
    onViewALlClick: () -> Unit,
) {
    Row(
        modifier = modifier.height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontWeight = FontWeight.ExtraBold)
        Text(
            text = "View All",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
                onViewALlClick.invoke()
            }
        )
    }
}