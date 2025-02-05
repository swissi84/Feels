package de.syntax_institut.feels.ui.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.ui.Models.MoodListViewModel
import de.syntax_institut.feels.ui.Views.ViewComponents.RoundedField

import de.syntax_institut.feels.ui.theme.FeelsTheme

@Composable
fun MoodDetailView(
    viewModel: MoodListViewModel = viewModel(),
    moodEntry: MoodEntry,
    modifier: Modifier = Modifier,
) {

    val isDarkMode by viewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color.Black else Color(0xFFFFE4C7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(modifier = Modifier
            .padding(30.dp),
            text = viewModel.MoodToEmoji(moodEntry.mood),
            style = MaterialTheme.typography.displayLarge
        )

        Text("Am ${viewModel.formatTimestamp(moodEntry.timestamp)} fühltest du dich:")

        Text(
            modifier = modifier
                .padding(16.dp),
            text = moodEntry.name,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp),
            text = moodEntry.moodText,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Beeinflusst durch:")
        Row {
            RoundedField(
                label = moodEntry.moodFactor,
                backgroundColor = Color(0xFF693BAB)
            ) { }

            RoundedField(
                label = moodEntry.moodWeather,
                backgroundColor = Color(0xFF2B65EC)
            ) { }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Stimmungs Wert:")
        Text(moodEntry.mood.toString())
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MoodDetailViewPreview() {
    val previewMood =
            MoodEntry(
                name = "Test1",
                mood = 5.0,
                moodFactor = "Familie",
                timestamp = System.currentTimeMillis(),
                moodText = "Test",
                moodWeather = "Sonnig"
            )
        MoodDetailView(
            moodEntry = previewMood
        )
    }
