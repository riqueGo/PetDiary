package com.example.petdiary.services.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object CastDates {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun fromDateToString(date: LocalDate): String {
        return date.format(formatter)
    }

    fun fromStringToDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter)
    }
}