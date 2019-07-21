package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.*

class SleepDetailViewModel(
        val sleepNightId: Long = 0L,
        val database: SleepDatabaseDao
): ViewModel() {

    // Coroutines job
    private val viewModelJob = Job()

    private val night = MediatorLiveData<SleepNight>()

    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNightId), night::setValue)
    }

    // The flag to move sleep tracker
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

    fun onNavigated() {
        _navigateToSleepTracker.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}