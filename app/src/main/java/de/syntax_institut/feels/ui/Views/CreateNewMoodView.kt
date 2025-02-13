@file:OptIn(ExperimentalLayoutApi::class)

package de.syntax_institut.feels.ui.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import de.syntax_institut.feels.ui.Models.MoodListViewModel
import de.syntax_institut.feels.ui.Views.ViewComponents.RoundedField
import de.syntax_institut.feels.ui.Views.ViewComponents.ShowAlertDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateNewMoodView(
    viewModel: MoodListViewModel = viewModel(),
    modifier: Modifier
) {

    var showDialog by remember { mutableStateOf(false) }

    val isDarkMode by viewModel.isDarkMode.collectAsState()

    var sliderValue by remember { mutableStateOf(5f) }
    var selectedMood by remember { mutableStateOf("😐") }

    val moodEmojis = listOf("😢", "😞", "😐", "😊", "😁", "🤩")
    var moodFactors = listOf("Finanzen", "Familie", "Gesundheit", "Arbeit", "Freizeit", "Liebe")
    val weatherFactors = listOf("☀️ Sonnig", "🌧 Regen", "☁️ Wolkig", "❄️ Kalt")

    var selectedMoodFactor by remember { mutableStateOf<String?>(null) }
    var selectedWeatherFactor by remember { mutableStateOf<String?>(null) }

    var newMoodName by remember { mutableStateOf("") }
    var newMoodText by remember { mutableStateOf("") }
    var newMoodFactor by remember { mutableStateOf("") }
    var newWeatherFactor by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val scrollState2 = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(
//                brush = Brush.linearGradient(
//                    colors = listOf(Color.White, Color(0xFFFFE4C7))
//                )
//            )
    ) {


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Wie geht es dir heute?", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newMoodName,
                onValueChange = { newMoodName = it },
                placeholder = { Text("Titel..") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text =  "Schlecht",
                    style =  MaterialTheme.typography.titleLarge
                )

                Text(
                    text =  "Super",
                    style =  MaterialTheme.typography.titleLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("")
                Slider(
                    value = sliderValue,
                    onValueChange = {
                        sliderValue = it
                        selectedMood = when {
                            it < 2 -> moodEmojis[0] // Sehr schlecht 😢
                            it < 4 -> moodEmojis[1] // Schlecht 😞
                            it < 6 -> moodEmojis[2] // Neutral 😐
                            it < 8 -> moodEmojis[3] // Gut 😊
                            it < 9 -> moodEmojis[4] // Sehr gut 😁
                            else -> moodEmojis[5] // Perfekt 🤩
                        }
                    },
                    valueRange = 1f..10f,
                    modifier = Modifier
                        .weight(1f),
                    colors = SliderDefaults.colors(thumbColor = Color.Yellow)
                )
                Text("")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(selectedMood, fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Stimmungs- und Wetterfaktoren:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState2),
                horizontalArrangement = Arrangement.Center,

                ) {

                moodFactors.forEach { factor ->
                    RoundedField(
                        label = factor,
                        backgroundColor = if (selectedMoodFactor == factor) Color(0xFF693BAB) else Color.Gray,
                        onClick = {
                            selectedMoodFactor = factor
                            newMoodFactor = factor
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.Center
            ) {
                weatherFactors.forEach { factor ->
                    RoundedField(
                        label = factor,
                        backgroundColor = if (selectedWeatherFactor == factor) Color(0xFF2B65EC) else Color.Gray,
                        onClick = {
                            selectedWeatherFactor = factor
                            newWeatherFactor = factor
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Für was bist du gerade dankbar?",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newMoodText,
                onValueChange = { newMoodText = it },
                placeholder = { Text("Text...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!newMoodName.isEmpty()) {
                ElevatedButton(
                    modifier = Modifier.padding(1.dp),
                    onClick = {
                        showDialog = true
                        viewModel.newMoodEntry(
                            mood = sliderValue.toDouble(),
                            moodFactor = newMoodFactor,
                            moodText = newMoodText,
                            name = newMoodName,
                            moodWeather = newWeatherFactor,
                        )
                        newMoodName = ""
                        newMoodText = ""
                    }
                )
                {
                    Text(
                        text =  "Speichern",
                        style =  MaterialTheme.typography.titleLarge
                    )
                }
            } else {
                ElevatedButton(
                    modifier = Modifier
                        .padding(1.dp)
                        .alpha(0.7f),
                    onClick = { }
                )
                {
                    Text(
                       text =  "Bitte gib einen Titel ein!",
                       style =  MaterialTheme.typography.titleLarge
                    )
                }
            }

            ShowAlertDialog(showDialog = showDialog, onDismiss = { showDialog = false })

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



