package com.example.roomquiz2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomquiz2.databinding.ActivityLogBinding

private lateinit var binding: ActivityLogBinding

lateinit var myObjects: ChosenObjects
lateinit var recyclerView:RecyclerView

@Suppress("DEPRECATION")
class ActivityLog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        binding = ActivityLogBinding.inflate(layoutInflater);
        val view = binding.root
        setContentView(view)
        binding.rView.layoutManager = LinearLayoutManager(this)

        val chosenObjects:ChosenObjects = intent.getParcelableExtra("logs")!!
        val adapter = CustomAdapter(chosenObjects.myArrayList,chosenObjects.myArrayList2)
        binding.rView.adapter = adapter


        binding.backbutton.setOnClickListener { myview ->
            finish()

        }




    }
}