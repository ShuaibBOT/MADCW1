package com.example.madcw1

import android.os.CountDownTimer

class DynamicCountDownTimer {
    private val timer = null
    private var negativeBias: Double = 0.00
    private var addingBias: Double = 0.00
    private var minutes: Long = 0
    private var ticks: Long = 0
    private var suppressFinish = false;

    fun DynamicCountdownTimer(minutes:Long, ticks:Long){
         setTimer(minutes,ticks)
    }
    fun updateMinutes(minutes: Long){
        if(timer != null){
            this.suppressFinish = true
            this.timer.cancel();
            this.timer = null
            this.minutes = seconds
            this.addingBias = this.negativeBias+this.addingBias
            setTimer(this.minutes,this.ticks)
        }

    }
    fun setTimer(minutes: Long,ticks:Long){
        this.minutes= minutes
        this.ticks= ticks
        var value= minutes*60*1000
        this.timer = CountDownTimer(value,ticks).also{
            override fun onTick(l:Long){
                negativeBias=((minutes*60*1000)-1).toDouble()

            }
        }

    }
    fun Start(){

    }
    fun Cancel(){

    }
}