package com.example.roomquiz2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.RoomDatabase

class QuestionViewModel(application: Application): AndroidViewModel(application) {
var questionIndex = 0
    lateinit var getQuestions:List<QuestionsOptionsTable>
    private lateinit var questionList:List<QuestionsOptionsTable>
    private lateinit var questionDao:QuestionDao

    lateinit var getLogQuestions:List<QuestionsOptionsTable>


    lateinit var db: QuestionsDatabase

}