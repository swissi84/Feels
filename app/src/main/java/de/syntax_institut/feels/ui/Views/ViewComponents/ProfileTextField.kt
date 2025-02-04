package de.syntax_institut.feels.ui.Views.ViewComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileTextField(label: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text(text = label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
    )
}