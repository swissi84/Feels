package de.syntax_institut.feels.ui.Models

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.data.MoodEntryDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.String as String
import de.syntax_institut.feels.dataStore
import kotlinx.coroutines.flow.first
import java.util.Date
import java.util.Locale

//DataStore
val NAME_KEY = booleanPreferencesKey("isSorted")

open class MoodListViewModel(application: Application) : AndroidViewModel(application) {
    //Room
    private val dao = MoodEntryDatabase.getDatabase(application.applicationContext).moodEntryDao()

    val items = dao.getAllItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val last7MoodEntries = dao.getLast7MoodEntries().stateIn(
          scope = viewModelScope,
           started = SharingStarted.Lazily,
           initialValue = emptyList())

    suspend fun getAverageMood(): Double {
        val sevenDaysAgo = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000 // 7 Tage in Millisekunden
        return dao.getAverageMoodLast7Days(sevenDaysAgo) ?: 0.0 // Falls null, dann 0.0 zurückgeben
    }

    //DataStore
    val datastore = application.dataStore

    private var _isSorted = MutableStateFlow(false)
    val isSorted = _isSorted.asStateFlow()

    init {
        //DataStore
        viewModelScope.launch {
            val allDataFromDataStore = datastore.data.first()
            val savedValue = allDataFromDataStore[NAME_KEY] ?: false
            _isSorted.value = savedValue
        }
    }

    //Room
    @RequiresApi(Build.VERSION_CODES.O)
    fun newMoodEntry(name: String, mood: Double, moodFactor: String, moodText: String, moodWeather:String ) {
        viewModelScope.launch {
            dao.insert(
                MoodEntry(
                    name = name,
                    moodText = moodText,
                    mood = mood,
                    moodFactor = moodFactor,
                    moodWeather = moodWeather,
                    timestamp = System.currentTimeMillis(),
                    )
            )
        }
    }

    //Room
    fun deleteTask(moodEntry: MoodEntry) {
        viewModelScope.launch {
            dao.delete(moodEntry)
        }
    }

    //DataStore
    fun sorting() {
        viewModelScope.launch {
            _isSorted.value = !_isSorted.value
            saveScoreToDataStore()
        }
    }

    //DataStore
    suspend fun saveScoreToDataStore() {
        datastore.edit { allMutableDataFromDataStore ->
            allMutableDataFromDataStore[NAME_KEY] = _isSorted.value
        }
    }

    fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun MoodToEmoji(mood: Double): String {
        val moodEmojis = listOf("😢", "😞", "😐", "😊", "😁", "🤩")

        return when {
            mood < 2 -> moodEmojis[0] // Sehr schlecht 😢
            mood < 4 -> moodEmojis[1] // Schlecht 😞
            mood < 6 -> moodEmojis[2] // Neutral 😐
            mood < 8 -> moodEmojis[3] // Gut 😊
            mood < 9 -> moodEmojis[4] // Sehr gut 😁
            else -> moodEmojis[5] // Perfekt 🤩
        }
    }
}