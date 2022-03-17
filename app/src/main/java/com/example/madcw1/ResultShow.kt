package com.example.madcw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultShow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_show)

        val correctCount = intent.getIntExtra("Correct_Answer",0)
        val incorrectCount = intent.getIntExtra("Incorrect_Answer",0)
        val totalCount = intent.getIntExtra("Total_Answer",0)

        val rightAnswerView = findViewById<TextView>(R.id.rightAnswerView)
        val wrongAnswerView = findViewById<TextView>(R.id.wrongAnswerView)
        val totalAnswerView = findViewById<TextView>(R.id.totalAnswerView)
        val bckToHomePgBtn = findViewById<Button>(R.id.bckToHomePgBtn)

        rightAnswerView.text = correctCount.toString()
        wrongAnswerView.text = incorrectCount.toString()
        totalAnswerView.text = totalCount.toString()

        bckToHomePgBtn.setOnClickListener {
            val backToMainIntent = Intent(this,MainActivity::class.java)
            startActivity(backToMainIntent)
        }
    }
}