/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    // Declare a job to cancel all coroutines
    private var viewModelJob = Job()

    // Call cancel all coroutines when view model was destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Define the scope for coroutines
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Define a value to get current night data
    private var tonight = MutableLiveData<SleepNight?>()

    // Define a value to get all night data from database
    val nights = database.getAllNights()

    // Transform list night to a string to display
    val nightsString = Transformations.map(nights) {nights ->
        formatNights(nights, application.resources)
    }

    // The value to notice started or not started tracking
    val startedTracking = Transformations.map(tonight) {
        it != null
    }

    // The value to notice not data to clear
    val isNothings = Transformations.map(nights) {
        it?.isEmpty()
    }

    // The value to navigate to quality screen
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    // The value to notice show snake bar
    private val _showSnakeBar = MutableLiveData<Boolean>()
    val showSnakeBar: LiveData<Boolean>
        get() = _showSnakeBar

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    // The function to get tonight from database, this is a suspend function
    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()

            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }

            night
        }
    }

    // Implement the function to handle on start button clicked
    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    // The function to insert data to database, it is a suspend function
    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    // Implement the function to make the handler for the stop button
    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    // The function to update data, it is a suspend function
    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    // The function to make the handler for the clear button
    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
            _showSnakeBar.value = true
        }
    }

    // Clear all data in database, it is a suspend fun
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    // The function to mapping finish navigate to quality screen
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    // The function to done show snake bar
    fun doneShowSnakeBar() {
        _showSnakeBar.value = false
    }
}

