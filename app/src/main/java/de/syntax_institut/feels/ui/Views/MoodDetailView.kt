package de.syntax_institut.feels.ui.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.ui.Models.MoodListViewModel

import de.syntax_institut.feels.ui.theme.FeelsTheme

@Composable
fun MoodDetailView(
    viewModel: MoodListViewModel = viewModel(),
    moodEntry: MoodEntry,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Text(moodEntry.name, style = MaterialTheme.typography.headlineLarge)

        Text(moodEntry.moodText, style = MaterialTheme.typography.titleSmall)


        Text(moodEntry.mood.toString())

        Spacer(modifier = Modifier.height(20.dp))

        Text(viewModel.formatTimestamp(moodEntry.timestamp))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MoodDetailViewPreview() {
    FeelsTheme {
        val previewMood =
            MoodEntry(
                name = "Test1",
                mood = 5.0,
                moodImage = "123",
                timestamp = System.currentTimeMillis(),
                moodText = "Test"
            )
        MoodDetailView(
            moodEntry = previewMood
)
    }
}