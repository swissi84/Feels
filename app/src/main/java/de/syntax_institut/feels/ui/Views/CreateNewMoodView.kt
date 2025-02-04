@file:OptIn(ExperimentalLayoutApi::class)

package de.syntax_institut.feels.ui.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.ui.Models.MoodListViewModel
import de.syntax_institut.feels.ui.theme.FeelsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateNewMoodView(
    viewModel: MoodListViewModel = viewModel(),
    modifier: Modifier
) {





    var sliderValue by remember { mutableStateOf(0.5f) }
    var selectedMood by remember { mutableStateOf("😐") }

    val moodEmojis = listOf("😞", "😐", "😃")
    val moodFactors = listOf("Finanzen", "Familie", "Gesundheit", "Arbeit")
    val weatherFactors = listOf("☀️ Sonnig", "🌧 Regen", "☁️ Wolkig", "❄️ Kalt")

    var newMoodName by remember { mutableStateOf("") }
    var newMoodText by remember { mutableStateOf("") }
    var newMoodImage by remember { mutableStateOf("") }

    var newMood by remember { mutableStateOf(5.0) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4C7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Wie geht es dir heute?", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newMoodName,
            onValueChange = { newMoodName = it },
            placeholder = { Text("Text..") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Schlimm")
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    selectedMood = when {
                        it < 0.33 -> moodEmojis[0]
                        it < 0.66 -> moodEmojis[1]
                        else -> moodEmojis[2]
                    }
                },
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(thumbColor = Color.Yellow)
            )
            Text("Super")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            Text(selectedMood, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Stimmungs- und Wetterfaktoren:", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            moodFactors.forEach { factor ->
                Chip(label = factor, backgroundColor = Color(0xFF8B5D5D))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            weatherFactors.forEach { factor ->
                Chip(label = factor, backgroundColor = Color(0xFF2B65EC))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Für was bist du gerade dankbar?", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newMoodText,
            onValueChange = { newMoodText = it },
            placeholder = { Text("Text...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedButton(
            modifier = Modifier.padding(1.dp),
            onClick = {
                viewModel.newMoodEntry(
                    mood = newMood,
                    moodImage = newMoodImage,
                    moodText = newMoodText,
                    name = newMoodName,
                )
            }
        )
        {
            Text("Speichern")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CreateNewMoodViewPreview() {
//    FeelsTheme {
//        val previewMood =
//            MoodEntry(
//                name = "Test1",
//                mood = 5.0,
//                moodImage = "123",
//                timestamp = System.currentTimeMillis(),
//                moodText = "Test"
//            )
//        CreateNewMoodView()
//    }
//}


@Composable
fun Chip(label: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 14.sp)
    }
}
