package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(finalScore : Int) : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score
    val scoreString = Transformations.map(_score) { scr ->
        scr.toString()
    }

    private val _playAgain = MutableLiveData<Boolean>()
    val playAgain : LiveData<Boolean>
        get() = _playAgain

    init {
        Timber.d("ScoreViewModel is initialized with final score: $finalScore")
        _score.value = finalScore
        _playAgain.value = false
    }

    fun playAgain() {
        _playAgain.value = true
    }

    fun playAgainComplete() {
        _playAgain.value = false
    }
}