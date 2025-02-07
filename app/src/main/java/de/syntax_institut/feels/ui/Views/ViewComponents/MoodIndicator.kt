package de.syntax_institut.feels.ui.Views.ViewComponents

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
        imageVector = if (averageMood >= 5) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,

        contentDescription = if (averageMood >= 5) "Positive Mood" else "Negative Mood",
        tint = if (averageMood >= 5) Color(0xFF4CAF50) else Color.Red,
        modifier = Modifier.size(100.dp)
    )
}
