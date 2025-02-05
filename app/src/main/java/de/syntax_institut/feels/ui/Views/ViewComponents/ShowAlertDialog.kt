package de.syntax_institut.feels.ui.Views.ViewComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowAlertDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Hinweis") },
            text = { Text("Möchtest du diese Aktion wirklich ausführen?") },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Abbrechen")
                }
            }
        )
    }
}
