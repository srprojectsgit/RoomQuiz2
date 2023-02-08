package com.example.roomquiz2

import android.renderscript.Int2
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertQuestion(question: QuestionsOptionsTable)

    @Query("SELECT * FROM questionsoptionstable")
    fun getAllQuestions(): List<QuestionsOptionsTable>

    @Query("DELETE from questionsoptionstable")
     fun deleteAll()

    @Query("SELECT * FROM questionsoptionstable WHERE question_id>= :myInt AND question_id< :myInt2")
      fun getLogQuestions(myInt:Int, myInt2: Int): List<QuestionsOptionsTable>





}