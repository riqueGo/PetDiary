package com.example.petdiary.domain.model

import java.time.LocalDate

data class DiaryNote(
    val id: Long,
    val title: String,
    val content: String,
    val date: LocalDate,
    val pets: List<Pet> = emptyList(),
    val images: List<String> = emptyList()
)