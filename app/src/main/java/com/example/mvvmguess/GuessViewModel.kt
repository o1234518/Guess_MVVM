package com.example.mvvmguess

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GuessViewModel: ViewModel() {
    val TAG = "Guess View Model"
    var count = 0
    var secretNumber = 0
    val counter = MutableLiveData<Int>()
    val result = MutableLiveData<GameResult>()

    init {
        reset()
        counter.value = count
    }

    fun reset() {
        count = 0
        counter.value = count
        secretNumber = Random.nextInt(10)+1
        Log.i(TAG, "${secretNumber}")
    }

    fun guess(guessNumber: Int) {
        val diff = secretNumber - guessNumber
        val gameResult = when(diff) {
            0 -> GameResult.BINGO
            in 1..Int.MAX_VALUE -> GameResult.BIGGER
            else -> GameResult.SMALLER
        }
        result.value = gameResult

        count ++
        counter.value = count
    }
}

enum class GameResult {
    BIGGER, SMALLER, BINGO
}