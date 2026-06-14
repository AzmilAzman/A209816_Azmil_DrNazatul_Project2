package com.example.a209816_azmil_nazatul_project02

import com.example.a209816_azmil_nazatul_project02.data.FeedbackDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class FeedbackRepository(private val feedbackDao: com.example.a209816_azmil_nazatul_project02.data.FeedbackDao) {

    private val firestore = FirebaseFirestore.getInstance()
    private val communityCollection = firestore.collection("community_feedback")

    val allHistory: Flow<List<com.example.a209816_azmil_nazatul_project02.HistoryItem>> = feedbackDao.getAllHistory()

    suspend fun insert(item: com.example.a209816_azmil_nazatul_project02.HistoryItem) {
        // 1. Save locally to Room
        feedbackDao.insertHistoryItem(item)

        // 2. Push to Firestore
        val firestoreData = hashMapOf(
            "category" to item.category,
            "message" to item.message,
            "date" to item.date,
            "status" to item.status,
            "latitude" to item.latitude,
            "longitude" to item.longitude,
            "imageUri" to item.imageUri
        )
        communityCollection.add(firestoreData).await()
    }
}