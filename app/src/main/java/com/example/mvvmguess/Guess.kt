package com.example.mvvmguess

import android.util.Log
import kotlin.math.roundToInt
import kotlin.random.Random

class Guess {
    val TAG = "Guess"
    var secretNumber = 0
    var counter = 0

    init {
        secretNumber = Random.nextInt(10)+1
        Log.i(TAG, "${secretNumber}")
    }

    fun refresh() {
        counter = 0
        secretNumber = Random.nextInt(10)+1
        Log.i(TAG, "${secretNumber}")
    }
}