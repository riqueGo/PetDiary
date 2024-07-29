package com.example.petdiary.domain.model

import java.time.LocalDateTime

data class Appointment(
    val id: Long = 0,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val description: String
)
