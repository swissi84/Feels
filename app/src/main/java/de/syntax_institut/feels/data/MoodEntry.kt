package de.syntax_institut.feels.data


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "moodEntrys")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val mood: Double,
    val moodImage: String,
    val moodText: String,
    val timestamp: Long,
    )




//val moodEntries = listOf(
//    MoodEntry(
//        name = "Test1",
//        mood = 5.0,
//        moodImage = "123",
//        selectedWeather = "Sonnig",
//        moodText = "Test"
//    ),
//
//    MoodEntry(
//        name = "Test2",
//        mood = 7.0,
//
//        moodImage = "123",
//        selectedWeather = "Bewölkt",
//        moodText = "Test"
//    ),
//
//    MoodEntry(
//        name = "Test1",
//        mood = 3.0,
//
//        moodImage = "123",
//        selectedWeather = "Sonnig",
//        moodText = "Test"
//    ),
//
//    MoodEntry(
//        name = "Test2",
//        mood = 2.0,
//
//        moodImage = "123",
//        selectedWeather = "Bewölkt",
//        moodText = "Test"
//    ),
//
//    MoodEntry(
//        name = "Test2",
//        mood = 8.0,
//
//        moodImage = "123",
//        selectedWeather = "Bewölkt",
//        moodText = "Test"
//    ),
//
//    MoodEntry(
//        name = "Test1",
//        mood = 9.0,
//
//        moodImage = "123",
//        selectedWeather = "Sonnig",
//        moodText = "Test"
//    ),
//
//    MoodEntry(
//        name = "Test2",
//        mood = 10.0,
//        moodImage = "123",
//        selectedWeather = "Bewölkt",
//        moodText = "Test"
//    )
//
//)