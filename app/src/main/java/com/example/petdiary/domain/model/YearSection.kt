package com.example.petdiary.domain.model

data class YearSection(
    val year: Int,
    val notes: List<DiaryNote>,
    var isExpanded: Boolean = true
)
