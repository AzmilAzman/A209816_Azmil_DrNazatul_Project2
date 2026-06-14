package com.example.a209816_azmil_nazatul_project02.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a209816_azmil_nazatul_project02.HistoryItem

// 1. Upgrade your version number here (increment it by 1)
@Database(entities = [_root_ide_package_.com.example.a209816_azmil_nazatul_project02.HistoryItem::class], version = 2, exportSchema = false)
abstract class FeedbackDatabase : RoomDatabase() {
    abstract fun feedbackDao(): com.example.a209816_azmil_nazatul_project02.data.FeedbackDao

    companion object {
        @Volatile
        private var INSTANCE: FeedbackDatabase? = null

        fun getDatabase(context: Context): FeedbackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedbackDatabase::class.java,
                    "feedback_database"
                )
                    // 2. Add this line right before .build() to auto-update schemas cleanly
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}