package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern : LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN),
    }

    companion object {
        const val DONE = 0L

        const val ONE_SECOND = 1000L

        const val GAME_TIME_COUNTDOWN = 60000L

        const val GAME_TIME_PANIC = 10L
    }

    // The timer
    private val timer : CountDownTimer

    // The current word
    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    // The game finish flag
    // The current word
    private val _gameEventFinish = MutableLiveData<Boolean>()
    val gameEventFinish : LiveData<Boolean>
        get() = _gameEventFinish

    // The buzz
    private val _buzzType = MutableLiveData<BuzzType>()
    val buzzType : LiveData<BuzzType>
        get() = _buzzType

    // The time of game
    private val _time = MutableLiveData<Long>()
    val time : LiveData<Long>
        get() = _time
    val timeString = Transformations.map(_time) { t ->
        DateUtils.formatElapsedTime(t)
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Timber.d("GameViewModel created!")
        resetList()
        nextWord()
        _score.value = 0
        _buzzType.value = BuzzType.NO_BUZZ
        timer = object : CountDownTimer(GAME_TIME_COUNTDOWN, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                val timeInSecond = millisUntilFinished / 1000
                _time.value = timeInSecond
                if (timeInSecond < GAME_TIME_PANIC) {
                    _buzzType.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _time.value = DONE
                _gameEventFinish.value = true
                _buzzType.value = BuzzType.GAME_OVER
            }
        }
        _time.value = (GAME_TIME_COUNTDOWN / 1000)
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("GameViewModel destroyed!")
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _buzzType.value = BuzzType.CORRECT
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _gameEventFinish.value = false
    }

    fun onBuzzComplete() {
        _buzzType.value = BuzzType.NO_BUZZ
    }
}