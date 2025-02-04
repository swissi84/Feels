package de.syntax_institut.feels.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "moodEntrys")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val mood: Double,
    val moodFactor: String,
    val moodWeather: String,
    val moodText: String,
    val timestamp: Long,
    )
