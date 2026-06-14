package com.example.a209816_azmil_nazatul_project02.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback_table")
data class FeedbackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val message: String,
    val date: String,
    val status: String = "Dihantar"
)