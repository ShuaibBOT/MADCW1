package com.example.madcw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.random.Random.Default.nextInt

class GameActivity : AppCompatActivity() {
    //Global variables
    var equationGenerated1 = generateRandEquation()
    var equationGenerated2 = generateRandEquation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val equation1TextView = findViewById<TextView>(R.id.equation1)
        val equation2TextView = findViewById<TextView>(R.id.equation2)
        val isGreaterBtn = findViewById<Button>(R.id.isGreaterBtn)
        val isLesserBtn = findViewById<Button>(R.id.isLesserBtn)
        val isEqualBtn = findViewById<Button>(R.id.isEqualBtn)
        val showResultView = findViewById<TextView>(R.id.showResultView)


        setEquationAndResult(equation1TextView,equation2TextView)

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
        }else{
            showResultView.text = "Incorrect"
        }

    }
    //checks if the result of the first equation is lesser than the second equation
    fun checkLesser(showResultView: TextView) {
        val passResult1 = equationGenerated1[1].toInt()
        val passResult2 = equationGenerated2[1].toInt()
        if (passResult1<passResult2){
            showResultView.text = "Correct"
        }else{
            showResultView.text = "Incorrect"
        }
    }
    //Checks if the result of both equations are equal
    fun checkEqual(showResultView: TextView) {
        val passResult1 = equationGenerated1[1].toInt()
        val passResult2 = equationGenerated2[1].toInt()
        if (passResult1==passResult2){
            showResultView.text = "Correct"
        }else{
            showResultView.text = "Incorrect"
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

}