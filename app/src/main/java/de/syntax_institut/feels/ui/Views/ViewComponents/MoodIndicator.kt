package de.syntax_institut.feels.ui.Views.ViewComponents

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.syntax_institut.feels.ui.Models.MoodListViewModel


@Composable
fun MoodIndicator(
    viewModel: MoodListViewModel,
    )
{
    var averageMood by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        averageMood = viewModel.getAverageMood()
    }

    Icon(
        imageVector = if (averageMood >= 5) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
        contentDescription = if (averageMood >= 5) "Positive Mood" else "Negative Mood",
        tint = if (averageMood >= 5) Color.Green else Color.Red,
        modifier = Modifier.size(80.dp)
    )
}
