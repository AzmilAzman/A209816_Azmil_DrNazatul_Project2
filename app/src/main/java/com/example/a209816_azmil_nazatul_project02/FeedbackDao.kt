package com.example.a209816_azmil_nazatul_project02.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.a209816_azmil_nazatul_project02.HistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedbackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryItem(item: com.example.a209816_azmil_nazatul_project02.HistoryItem)

    @Query("SELECT * FROM feedback_table ORDER BY id DESC")
    fun getAllHistory(): Flow<List<com.example.a209816_azmil_nazatul_project02.HistoryItem>>
}