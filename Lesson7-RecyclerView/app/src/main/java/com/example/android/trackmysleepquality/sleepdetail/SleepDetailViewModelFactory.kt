package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import java.lang.IllegalArgumentException

class SleepDetailViewModelFactory(
        val sleepNightId: Long = 0L,
        val dataSource: SleepDatabaseDao
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepNightId, dataSource) as T
        }
        throw IllegalArgumentException("ViewModel invalid")
    }
}