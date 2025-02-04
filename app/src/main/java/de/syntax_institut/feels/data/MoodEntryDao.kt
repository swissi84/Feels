package de.syntax_institut.feels.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodEntryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moodEntrys: MoodEntry)

    @Query("SELECT * from moodEntrys ORDER BY id ASC")
    fun getAllItems(): Flow<List<MoodEntry>>

    @Query("SELECT * FROM moodEntrys ORDER BY timestamp DESC LIMIT 7")
    fun getLast7MoodEntries(): Flow<List<MoodEntry>>

    @Query("SELECT AVG(mood) FROM moodEntrys WHERE timestamp >= :sevenDaysAgo")
    suspend fun getAverageMoodLast7Days(sevenDaysAgo: Long): Double?

    @Delete
    suspend fun delete(moodEntrys: MoodEntry)

    @Update
    suspend fun update(moodEntrys: MoodEntry)
}