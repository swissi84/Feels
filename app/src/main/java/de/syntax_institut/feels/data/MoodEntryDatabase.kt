package de.syntax_institut.feels.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoodEntry::class], version = 1, exportSchema = false)
abstract class MoodEntryDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao

    companion object {
        @Volatile
        private var Instance: MoodEntryDatabase? = null

        fun getDatabase(context: Context): MoodEntryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MoodEntryDatabase::class.java, "moodEntry_database")
                    .build().also { Instance = it }
            }
        }
    }
}