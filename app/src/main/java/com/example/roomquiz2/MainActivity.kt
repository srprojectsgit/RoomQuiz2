package com.example.roomquiz2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roomquiz2.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var questionDao: QuestionDao
    private lateinit var questionList: List<QuestionsOptionsTable>
    lateinit var binding: ActivityMainBinding
    var questionIndex = 0
    var tempList = ArrayList<String>()
    var button1Clicked = false
    var button2Clicked = false
    var button3Clicked = false
    var button4Clicked = false
    lateinit var getQuestions: List<QuestionsOptionsTable>
    var atemp = true

    private lateinit var androidViewModel: QuestionViewModel


    lateinit var handler: Handler

    var nextQuestion = ""
    var nextAnswer = ""
    var nextOption1 = ""
    var nextOption2 = ""
    var nextOption3 = ""

    var answer = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root


        androidViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)


        handler = Handler(Looper.getMainLooper())


        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val myInt = sharedPref.getInt("My Index Point", 0)
        androidViewModel.questionIndex = myInt


        val db = Room.databaseBuilder(
            applicationContext,
            QuestionsDatabase::class.java, "questions_database"
        ).build()

        questionDao = db.questionDao()

        binding.button.setOnClickListener {
            androidViewModel.questionIndex++
            checkRight(binding.button)
            delayAnimation()
        }

        binding.button2.setOnClickListener {
            androidViewModel.questionIndex++
            checkRight(binding.button2)


            delayAnimation()

        }

        binding.button3.setOnClickListener {

            androidViewModel.questionIndex++

            checkRight(binding.button3)

            delayAnimation()

        }

        binding.button4.setOnClickListener {

            androidViewModel.questionIndex++

            checkRight(binding.button4)
            delayAnimation()

        }

        binding.answerLog.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                var myVar = 0
                if (androidViewModel.questionIndex >= 10) {
                    myVar = androidViewModel.questionIndex - 10
                }






                androidViewModel.getLogQuestions =
                    questionDao.getLogQuestions(myVar, androidViewModel.questionIndex + 1)

                var questionLogList = ArrayList<String>()
                var answerLogList = ArrayList<String>()

                for (qval in 0 until androidViewModel.getLogQuestions.size) {
                    questionLogList.add(androidViewModel.getLogQuestions[qval].question)
                    answerLogList.add(androidViewModel.getLogQuestions[qval].answer)
                }


                val myLogs: ChosenObjects = ChosenObjects(questionLogList, answerLogList)
                val intent = Intent(applicationContext, ActivityLog::class.java)
                intent.putExtra("logs", myLogs)

                startActivity(intent)


//                withContext(Dispatchers.Main){
//                Toast.makeText(applicationContext,androidViewModel.getLogQuestions[0].question,Toast.LENGTH_LONG).show()}


            }
        }


        testDatabase()
        setContentView(view)


    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt("My Index Point", androidViewModel.questionIndex)//androidViewModel.questionIndex
            apply()
        }
    }

    private fun testDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            //questionDao.deleteAll()

            questionList = listOf(
                QuestionsOptionsTable(
                    0,
                    "What is the capital of Columbia",
                    "Bogota",
                    "La Paz",
                    "Lima",
                    "Santa Fe"
                ),
                QuestionsOptionsTable(
                    0,
                    "What is Tricholo Methane better known as?",
                    "Chloroform",
                    "Chlorine",
                    "Helium",
                    "Potassium"
                ),
                QuestionsOptionsTable(
                    0,
                    "What is Calcium Carbonate most commonly known as",
                    "Chalk",
                    "Cheese",
                    "Salt",
                    "Sugar"
                ),
                QuestionsOptionsTable(
                    0,
                    "Who won a noble prize for chemistry for his work on the structure of protein",
                    "Frederick Sanger",
                    "Humphry Davy",
                    "John Dalton",
                    "Linus Pauling"
                ),
                QuestionsOptionsTable(
                    0,
                    "What is Acetic Acid commonly known as",
                    "Vinegar",
                    "Salt",
                    "Sugar",
                    "Ketchup"
                ),
                QuestionsOptionsTable(
                    0,
                    "How many karats are there in pure gold?",
                    "24",
                    "32",
                    "8",
                    "16"
                ),
                QuestionsOptionsTable(
                    0,
                    "Around the altar of which god were the earliest Greek dramas performed?",
                    "Dionysus",
                    "Zeus",
                    "Ares",
                    "Hades"
                ),
                QuestionsOptionsTable(
                    0,
                    "The Witch Circe turns Odysseus men into what animal?",
                    "Pigs",
                    "Donkeys",
                    "Eagles",
                    "Toads"
                ),
                QuestionsOptionsTable(
                    0,
                    "How long did Methuselah live?",
                    "969 years",
                    "33 years",
                    "100 years",
                    "28 years"
                ),
                QuestionsOptionsTable(
                    0,
                    "Which of Shakespeare's plays is the shortest?",
                    "Comedy of Errors",
                    "All's well that ends well",
                    "Twelfth Knight",
                    "Much Ado about Nothing"
                ),
                QuestionsOptionsTable(
                    0,
                    "Which street was William Shakespeare born?",
                    "Henley Street",
                    "Arden Street",
                    "Stratford Street",
                    "Avon Street"
                ),
                QuestionsOptionsTable(
                    0,
                    "In Shakespeare's measure for measure, what was the name of the clown",
                    "Pompey",
                    "Coco",
                    "Juggles",
                    "Benvolio"
                ),
                QuestionsOptionsTable(
                    0,
                    "Which of Shakespeare's plays ends with the noble Trojan Hector's death?",
                    "Troilus and Cressida",
                    "Anthony and Cleopatra",
                    "Henvry VII",
                    "Julius Caesar"
                ),
                QuestionsOptionsTable(
                    0,
                    "\"What's past is prologue\" is from what Shakespeare play?",
                    "The Tempest",
                    "Macbeth",
                    "Taming of the Shrew",
                    "A Midsummer Knights Dream"
                ),

                QuestionsOptionsTable(
                    0,
                    "Who was the first king of Israel?",
                    "Saul",
                    "Walter",
                    "Gus",
                    "Jesse"
                ),

                QuestionsOptionsTable(
                    0,
                    "What type of rocks form when bits of rock are squashed into layers?",
                    "Sedimentary",
                    "Igneous",
                    "Basalt",
                    "Metamohphic",

                ),

                QuestionsOptionsTable(
                    0,
                    "Which of these is a Metamorphic Rock?",
                    "Marble",
                    "Granite",
                    "Chalk",
                    "Limestone"
                )

            ,
                QuestionsOptionsTable(
                    0,
                    "Which rock can float on water?",
                    "Pumice",
                    "Granite",
                    "Lead",
                    "Obsidian"
                )





            ).shuffled()


            if(questionDao.getAllQuestions().isEmpty()){

            for (temp in 0 until questionList.size) {
                questionDao.insertQuestion(questionList.get(temp))
            }}


            androidViewModel.getQuestions = questionDao.getAllQuestions()



            withContext(Dispatchers.Main) {
                binding.textView.text =
                    androidViewModel.getQuestions[androidViewModel.questionIndex].question
                answer = androidViewModel.getQuestions[androidViewModel.questionIndex].answer

                if (tempList.size > 0) {
                    tempList.clear()
                }
                tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].answer)
                tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].option1)
                tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].option2)
                tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].option3)



                tempList.shuffle()

                binding.button.text = tempList[0]
                binding.button2.text = tempList[1]
                binding.button3.text = tempList[2]
                binding.button4.text = tempList[3]


            }
        }
    }


    fun moveToNext() {


        if (androidViewModel.questionIndex > androidViewModel.getQuestions.size) {
            androidViewModel.questionIndex = 0
        }

        tempList.clear()

        binding.textView.text =
            androidViewModel.getQuestions[androidViewModel.questionIndex].question
        answer = androidViewModel.getQuestions[androidViewModel.questionIndex].answer

        tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].answer)
        tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].option1)
        tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].option2)
        tempList.add(androidViewModel.getQuestions[androidViewModel.questionIndex].option3)



        tempList.shuffle()

        binding.button.text = tempList[0]
        binding.button2.text = tempList[1]
        binding.button3.text = tempList[2]
        binding.button4.text = tempList[3]

        changeColor()

    }

    fun checkRight(b: TextView) {

//        var getQuestions = questionDao.getAllQuestions()
//
//        var answer = getQuestions[questionIndex]
//
//        binding.button.isClickable = false
//        binding.button2.isClickable = false
//        binding.button3.isClickable = false
//        binding.button4.isClickable = false


        if (b.text.toString().equals(answer)) {
            b.setBackgroundColor(getColor(R.color.srgreen))
        } else {
            if (binding.button.text.toString().equals(answer)) {
                binding.button.setBackgroundResource(R.color.srgreen)
            } else if (binding.button2.text.toString().equals(answer)) {
                binding.button2.setBackgroundResource(R.color.srgreen)
            } else if (binding.button3.text.toString().equals(answer)) {
                binding.button3.setBackgroundResource(R.color.srgreen)
            } else if (binding.button4.text.toString().equals(answer)) {
                binding.button4.setBackgroundResource(R.color.srgreen)
            }
            b.setBackgroundColor(Color.RED)

        }


    }

    fun delayAnimation() {


//
//        if(button1Clicked){
//            checkRight(binding.button)
//        }
//
//        else if(button2Clicked){
//            checkRight(binding.button2)
//        }
//
//        else if(button3Clicked){
//            checkRight(binding.button3)
//        }
//
//        else if(button4Clicked){
//            checkRight(binding.button4)
//        }

        lifecycleScope.launch(Dispatchers.Main) {

            if (androidViewModel.questionIndex > androidViewModel.getQuestions.size - 1) {
                androidViewModel.questionIndex = 0
            }

            delay(2000)
            moveToNext()
        }
//        handler = Handler(Looper.getMainLooper())
//            handler.postDelayed(
//            {moveToNext()},2000
//        )


        button1Clicked = false;
        button2Clicked = false;
        button3Clicked = false;
        button4Clicked = false;


    }

    fun changeColor() {
        binding.button.setBackgroundColor(Color.GRAY)
        binding.button2.setBackgroundColor(Color.GRAY)
        binding.button3.setBackgroundColor(Color.GRAY)
        binding.button4.setBackgroundColor(Color.GRAY)

    }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}