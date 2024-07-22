package com.example.petdiary.ui.appointment

import java.time.LocalDate
import java.time.LocalTime

data class Appointment(
    val title: String,
    val date: LocalDate,
    val time: LocalTime,
    val hasAlarm: Boolean,
    val description: String
)