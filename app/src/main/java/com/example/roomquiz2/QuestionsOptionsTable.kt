package com.example.roomquiz2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionsOptionsTable(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "question_id")

  val id:Int,

  @ColumnInfo(name = "question")
  val question:String,

  @ColumnInfo(name = "answer")
  val answer:String,

  @ColumnInfo(name = "option_1")
  val option1:String,

  @ColumnInfo(name = "option_2")
  val option2:String,

  @ColumnInfo(name = "option_3")
  val option3:String

)