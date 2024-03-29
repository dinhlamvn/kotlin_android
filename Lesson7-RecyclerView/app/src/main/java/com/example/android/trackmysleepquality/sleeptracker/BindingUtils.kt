package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertLongToDateString
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

@BindingAdapter("sleepDuration")
fun TextView.sleepDuration(item: SleepNight?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, resources)
    }
}

@BindingAdapter("sleepQuality")
fun TextView.sleepQuality(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, resources)
    }
}

@BindingAdapter("sleepQualityImage")
fun ImageView.sleepQualityImage(item: SleepNight?) {
    item?.let {
        setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_launcher_sleep_tracker_foreground
                }
        )
    }
}

@BindingAdapter("timeString")
fun TextView.timeString(time: Long?) {
    time?.let {
        text = convertLongToDateString(it)
    }
}