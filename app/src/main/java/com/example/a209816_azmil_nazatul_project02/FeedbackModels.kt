package com.example.a209816_azmil_nazatul_project02

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FeedbackData(
    val category: String = "",
    val message: String = ""
)

data class UserProfile(
    val name: String = "Azmil",
    val studentId: String = "A209816",
    val phone: String = "012-3456789"
)

data class FAQItem(
    val question: String,
    val answer: String
)

@Entity(tableName = "feedback_table")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val message: String,
    val date: String,
    val status: String,

    // ADD THESE THREE LINES FOR CAMERA AND GPS STORAGE:
    val imageUri: String = "",
    val latitude: String = "",
    val longitude: String = ""
)