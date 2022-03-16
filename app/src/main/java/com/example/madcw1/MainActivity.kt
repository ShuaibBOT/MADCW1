package com.example.madcw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startGameBtn = findViewById<Button>(R.id.strtGame)
        val aboutGameBtn = findViewById<Button>(R.id.abtBtn)

        startGameBtn.setOnClickListener {
            val startGameIntent= Intent(this,GameActivity::class.java)
            startActivity(startGameIntent)
        }
        aboutGameBtn.setOnClickListener {
            val abtGameIntent=Intent(this,AboutPage::class.java)
            startActivity(abtGameIntent)
        }
    }
}