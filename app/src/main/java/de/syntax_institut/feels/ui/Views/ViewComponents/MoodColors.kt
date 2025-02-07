package de.syntax_institut.feels.ui.Views.ViewComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getMoodColor(mood: Int): Color {
    return when (mood) {
        in 0..1 -> Color(0xFFFF3030)
        in 2..3 -> Color(0xFFFF6363)
        in 4..5 -> Color(0xFFFFB24A)
        in 6..7 -> Color(0xFFFF9418)
        in 8..9 -> Color(0xFF5BBD5F)
        in 9..10 -> Color(0xFF22A928)
        else -> Color.White
    }
}
