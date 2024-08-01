package com.example.petdiary.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
object CalendarPicker {
    fun showDatePicker(fragmentManager: FragmentManager, callback: (LocalDate) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.addOnPositiveButtonClickListener {
            val localDate = Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
            callback(localDate)
        }
        datePicker.show(fragmentManager, "datePicker")
    }

    fun showTimePicker(fragmentManager: FragmentManager, callback: (LocalTime) -> Unit) {
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
        timePicker.addOnPositiveButtonClickListener {
            val localTime = LocalTime.of(timePicker.hour, timePicker.minute)
            callback(localTime)
        }
        timePicker.show(fragmentManager, "timePicker")
    }
}