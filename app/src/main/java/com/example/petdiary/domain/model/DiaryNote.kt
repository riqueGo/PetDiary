package com.example.petdiary.domain.model

import java.time.LocalDateTime

data class DiaryNote(
    val id: Long,
    val title: String,
    val content: String,
    val date: LocalDateTime
)