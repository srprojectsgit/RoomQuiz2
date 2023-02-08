package com.example.roomquiz2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuestionsOptionsTable::class], version = 2)
abstract class QuestionsDatabase : RoomDatabase() {
  abstract fun questionDao(): QuestionDao
}