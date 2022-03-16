package com.example.madcw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AboutPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)

        val homePgBtn = findViewById<Button>(R.id.hmPgBtn)

        homePgBtn.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java )
            startActivity(mainIntent)
        }
    }
}