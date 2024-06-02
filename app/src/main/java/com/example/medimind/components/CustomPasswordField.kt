package com.example.medimind.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomPasswordField(
    value: String,
    labelValue: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = RoundedCornerShape(10),
    readOnly: Boolean = false,
    startPadding: Dp = 0.dp,
    endPadding: Dp = 0.dp,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
    errorMessage: String? = null
) {

    val localFocusManager = LocalFocusManager.current

    val passwordVisibility = remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start =  startPadding, top = topPadding, end = endPadding, bottom = bottomPadding
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onPasswordChange(it)
            },
            label = { Text(text = labelValue) },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisibility.value) "hide password" else "show password",
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            },
            shape = shape,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            readOnly = readOnly,
            isError = errorMessage != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                focusedPlaceholderColor = Color.Black,
                focusedSupportingTextColor = Color.Black,
                focusedPrefixColor = Color.Black,
                focusedSuffixColor = Color.Black,
                focusedTrailingIconColor = Color.Black
            )
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}