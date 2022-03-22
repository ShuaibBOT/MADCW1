package com.example.madcw1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.random.Random.Default.nextInt

class GameActivity : AppCompatActivity() {
    //Global variables
    var equationGenerated1 = generateRandEquation()
    var equationGenerated2 = generateRandEquation()
    var correctCount = 0
    var incorrectCount = 0
    var correctCount5=0
    var got5Correct = false
    val countDownTime: Long = 10000
    var timeLeft: Long = 0
    var stopTimer = false

    //key to read, for orientation change
    val CORRECT_COUNT: String = "CORRECT_COUNT"
    val INCORRECT_COUNT: String = "INCORRECT_COUNT"
    val CORRECT_COUNT_5: String ="CORRECT_COUNT_5"
    val TIME_LEFT:String ="TIME_LEFT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if(savedInstanceState != null){
            correctCount = savedInstanceState.getInt(CORRECT_COUNT)
            incorrectCount = savedInstanceState.getInt(INCORRECT_COUNT)
            correctCount5 = savedInstanceState.getInt(CORRECT_COUNT_5)
            timeLeft= savedInstanceState.getLong(TIME_LEFT)

            
        }


        val equation1TextView = findViewById<TextView>(R.id.equation1)
        val equation2TextView = findViewById<TextView>(R.id.equation2)
        val isGreaterBtn = findViewById<Button>(R.id.isGreaterBtn)
        val isLesserBtn = findViewById<Button>(R.id.isLesserBtn)
        val isEqualBtn = findViewById<Button>(R.id.isEqualBtn)
        val showResultView = findViewById<TextView>(R.id.showResultView)
        val countDownView = findViewById<TextView>(R.id.countDownView)


        setEquationAndResult(equation1TextView,equation2TextView)

        //Count Down Timer
        object : CountDownTimer(countDownTime-timeLeft, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                countDownView.setText("Seconds remaining: \n" + millisUntilFinished / 1000)
                timeLeft+= 1000
                while(stopTimer){
                    super.cancel()
                }
            }

            override fun onFinish() {
                countDownView.setText("done!")
                //call for  result activity
                callResultShowActivity()
            }
        }.start()

        isGreaterBtn.setOnClickListener {
            checkGreater(showResultView)
            setEquationAndResult(equation1TextView,equation2TextView)
        }
        isLesserBtn.setOnClickListener {
            checkLesser(showResultView)
            setEquationAndResult(equation1TextView,equation2TextView)
        }
        isEqualBtn.setOnClickListener {
            checkEqual(showResultView)
            setEquationAndResult(equation1TextView,equation2TextView)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        stopTimer= true
        outState.putInt(CORRECT_COUNT,correctCount)
        outState.putInt(CORRECT_COUNT_5,correctCount5)
        outState.putInt(INCORRECT_COUNT,incorrectCount)
        outState.putLong(TIME_LEFT,timeLeft)

    }
    fun setEquationAndResult(equation1TextView:TextView,equation2TextView:TextView){
        equationGenerated1 = generateRandEquation()
        equationGenerated2 = generateRandEquation()

        while (equationGenerated1[1].toInt()>100 || equationGenerated2[1].toInt()>100){
            equationGenerated1= generateRandEquation()
            equationGenerated2 = generateRandEquation()
        }
        equation1TextView.text = equationGenerated1[0]+" = "+equationGenerated1[1]
        equation2TextView.text = equationGenerated2[0]+" = "+equationGenerated2[1]

    }

    //Checks if the result of first equation is greater than the second equation.
    fun checkGreater(showResultView: TextView) {
        val passResult1 = equationGenerated1[1].toInt()
        val passResult2 = equationGenerated2[1].toInt()
        if (passResult1>passResult2){
            showResultView.text = "Correct"
            showResultView.setTextColor(Color.parseColor("#00FF00"))
            correctCount++
            correctCount5++
            if(correctCount5 == 5){
                got5Correct= true
                correctCount5=0
            }
        }else{
            showResultView.text = "Incorrect"
            showResultView.setTextColor(Color.parseColor("#FF0000"))
            incorrectCount++
        }

    }
    //checks if the result of the first equation is lesser than the second equation
    fun checkLesser(showResultView: TextView) {
        val passResult1 = equationGenerated1[1].toInt()
        val passResult2 = equationGenerated2[1].toInt()
        if (passResult1<passResult2){
            showResultView.text = "Correct"
            showResultView.setTextColor(Color.parseColor("#00FF00"))
            correctCount++
            correctCount5++
            if(correctCount5 == 5){
                got5Correct= true
                correctCount5=0
            }
        }else{
            showResultView.text = "Incorrect"
            showResultView.setTextColor(Color.parseColor("#FF0000"))
            incorrectCount++
        }
    }
    //Checks if the result of both equations are equal
    fun checkEqual(showResultView: TextView) {
        val passResult1 = equationGenerated1[1].toInt()
        val passResult2 = equationGenerated2[1].toInt()
        if (passResult1==passResult2){
            showResultView.text = "Correct"
            showResultView.setTextColor(Color.parseColor("#00FF00"))
            correctCount++
            correctCount5++
            if(correctCount5 == 5){
                got5Correct= true
                correctCount5=0
            }
        }else{
            showResultView.text = "Incorrect"
            showResultView.setTextColor(Color.parseColor("#FF0000"))
            incorrectCount++
        }
    }


    fun generateRandEquation(): MutableList<String>{

        //generate Random numbers
        val randGenTerms = mutableListOf<Int>()
        val numberOfTerm = (2..4).random()
        repeat(numberOfTerm){
            randGenTerms.add((1..20).random())
        }
        Log.d("Term list",randGenTerms.toString())
        Log.d("Term Size",randGenTerms.size.toString())


        //generate Random operation
        val randGenOper = mutableListOf<String>()
        val operationList= listOf<String>("/","*","+","-")

        repeat(numberOfTerm-1){
            randGenOper.add(operationList[(0..3).random()])
        }
        Log.d("Operator list",randGenOper.toString())
        Log.d("operator size",randGenOper.size.toString())

        //Duplicate List
        var duplicateRandGenTerms = mutableListOf<Int>()
        var duplicateRandGenOper = mutableListOf<String>()

        duplicateRandGenTerms.addAll(randGenTerms)
        duplicateRandGenOper.addAll(randGenOper)

        //Calculates the equation
        val equationResult = doesArithmetics(duplicateRandGenTerms,duplicateRandGenOper,randGenTerms)

        Log.d("Check if correct", "Duplicate terms: "+duplicateRandGenTerms.toString())
        Log.d("Check if correct ","original terms: "+randGenTerms.toString())
        //produce equation
        var equationProduced:String=""
        when (randGenTerms.size){
            2 -> equationProduced = randGenTerms[0].toString()+randGenOper[0]+randGenTerms[1].toString()
            3 -> equationProduced = "("+randGenTerms[0].toString()+randGenOper[0]+randGenTerms[1].toString()+")"+randGenOper[1]+randGenTerms[2].toString()
            4 -> equationProduced =  "("+"("+randGenTerms[0].toString()+randGenOper[0]+randGenTerms[1].toString()+")"+randGenOper[1]+randGenTerms[2].toString()+")"+randGenOper[2]+randGenTerms[3].toString()
            else-> {
                Log.d("Something", "Stopped working ")
            }
        }
        var transferItems = mutableListOf(equationProduced,equationResult)
        return transferItems;
    }
    fun doesArithmetics(randGenTerms:MutableList<Int>, randGenOp:MutableList<String>, originalRandGenTerms: MutableList<Int>):String {
        for(i in 0 until randGenOp.size){
            Log.d("Arithmetic", "Count:"+i.toString())
            Log.d("doesArithmeticsFunction","Enter loop in does arithmetic function")
            if (randGenOp[i] == "/"){
                Log.d("Enter","Comes to point 1 division")
                //Checks if denominator is a factor of the numerator
                while(randGenTerms[0]%randGenTerms[1] !=0){
                    randGenTerms[1]=(1..20).random()
                }
                Log.d("randGenTerm", "randgenTermCangedTo"+randGenTerms[1])
                originalRandGenTerms[i+1]= randGenTerms[1]
                randGenTerms[0] = randGenTerms[0] / randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                pushUnwantedToRight(randGenTerms)
                randGenTerms.removeLast()
                Log.d("Value after function",randGenTerms.toString())
            }else if (randGenOp[i] == "*"){
                Log.d("Enter","Comes to point 1 Multiplication")
                randGenTerms[0] = randGenTerms[0] * randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                pushUnwantedToRight(randGenTerms)
                randGenTerms.removeLast()
                Log.d("Value after function",randGenTerms.toString())
            }else if(randGenOp[i] == "+"){
                Log.d("Enter","Comes to point 1 Addition")
                randGenTerms[0] = randGenTerms[0] + randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                pushUnwantedToRight(randGenTerms)
                randGenTerms.removeLast()
                Log.d("Value after function",randGenTerms.toString())
            }else if (randGenOp[i] == "-"){
                Log.d("Enter","Comes to point 1 Subtraction")
                randGenTerms[0] = randGenTerms[0] - randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                pushUnwantedToRight(randGenTerms)
                randGenTerms.removeLast()
                Log.d("Value after function",randGenTerms.toString())
            }else{
                Log.d("error","Operator not recognized")
            }
        }
        var equationResult = randGenTerms[0]
        Log.d("AfterArithmetics", randGenTerms.toString())
        return equationResult.toString()
    }
    fun pushUnwantedToRight( randGenTerms: MutableList<Int>){
        Log.d("Size of randGenTerms", randGenTerms.size.toString())
        for(i in 1 until randGenTerms.size-1){
            Log.d("error","loop "+i.toString())
            var temp = randGenTerms[i]
            randGenTerms[i] = randGenTerms[i+1]
            randGenTerms[i+1]=temp
        }
        Log.d("Left For loop","Left Loop at function pushUnwantedToRight")
    }
    fun callResultShowActivity(){
        val resultShowIntent= Intent(this,ResultShow::class.java)
        val totalCount= correctCount+incorrectCount
        resultShowIntent.putExtra("Correct_Answer",correctCount)
        resultShowIntent.putExtra("Incorrect_Answer",incorrectCount)
        resultShowIntent.putExtra("Total_Answer",totalCount)
        startActivity(resultShowIntent)
    }

}