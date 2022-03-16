package com.example.madcw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.*
import kotlin.random.Random.Default.nextInt

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val equation1TextView = findViewById<TextView>(R.id.equation1)
        val equation2TextView = findViewById<TextView>(R.id.equation2)

        val equationGenerated1 = generateRandEquation()
        val equationGenerated2 = generateRandEquation()

        equation1TextView.text = equationGenerated1[0]+" = "+equationGenerated1[1]
        equation2TextView.text = equationGenerated2[0]+" = "+equationGenerated2[1]

    }

    fun generateRandEquation(): List<String>{

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

        //Check if denominator is factor of numerator
//        checkIfDenominatorFactorOfNumerator(randGenTerms,randGenOper)
        //Duplicate List
        var duplicateRandGenTerms = mutableListOf<Int>()
        var duplicateRandGenOper = mutableListOf<String>()

        duplicateRandGenTerms.addAll(randGenTerms)
        duplicateRandGenOper.addAll(randGenOper)
        //Calculates the equation
        val equationResult = doesArithmetics(duplicateRandGenTerms,duplicateRandGenOper)

        Log.d("Check if correct", duplicateRandGenTerms.toString() )
        Log.d("Check if correct", randGenTerms.toString())
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
        val tranferItems = listOf<String>(equationProduced,equationResult)
        return tranferItems;
    }
    fun doesArithmetics(randGenTerms:MutableList<Int>, randGenOp:MutableList<String>):String {
        for(i in 0 until randGenOp.size){
            Log.d("Arithmetic", "Count:"+i.toString())
            Log.d("doesArithmeticsFunction","Enter loop in does arithmetic function")
            if (randGenOp[i] == "/"){
                randGenTerms[0] = randGenTerms[0] / randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                Log.d("Enter","Comes to point 1 division")
                pushUnwantedToRight(randGenTerms)
                Log.d("Value after function",randGenTerms.toString())
            }else if (randGenOp[i] == "*"){
                randGenTerms[0] = randGenTerms[0] * randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                Log.d("Enter","Comes to point 1 Multiplication")
                pushUnwantedToRight(randGenTerms)
                Log.d("Value after function",randGenTerms.toString())
            }else if(randGenOp[i] == "+"){
                randGenTerms[0] = randGenTerms[0] + randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                Log.d("Enter","Comes to point 1 Addition")
                pushUnwantedToRight(randGenTerms)
                Log.d("Value after function",randGenTerms.toString())
            }else if (randGenOp[i] == "-"){
                randGenTerms[0] = randGenTerms[0] - randGenTerms[1]
                Log.d("output value of index 0", randGenTerms[0].toString())
                Log.d("Enter","Comes to point 1 Subtraction")
                pushUnwantedToRight(randGenTerms)
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
        Log.d("Error","Comes to point 2")
    }
//    fun checkIfDenominatorFactorOfNumerator(randGenTerms: MutableList<Int>, randGenOp: MutableList<String>){
//        for (i in 0 until randGenOp.size ){
//            if (randGenOp[i] == "/"){
//                while(randGenTerms[i]%randGenTerms[i+1] !=0){
//                    randGenTerms[i+1]=(1..20).random()
//                }
//            }
//        }
//    }
}