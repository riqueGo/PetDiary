package com.example.petdiary.domain.model

import java.time.LocalDate

data class Pet(
    val name: String,
    val species: String,
    val race: String,
    val birthday: LocalDate
)
