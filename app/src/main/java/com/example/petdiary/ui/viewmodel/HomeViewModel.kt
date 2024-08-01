package com.example.petdiary.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petdiary.domain.model.DiaryNote
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel : ViewModel() {

    private val _yearSections = MutableLiveData<Map<Int, List<DiaryNote>>>()
    val yearSections: LiveData<Map<Int, List<DiaryNote>>> = _yearSections

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            // Mock data
            val notes = listOf(
                DiaryNote(
                    id = 1,
                    title = "First Note",
                    content = "This is the first note.",
                    date = LocalDate.of(2022, 1, 1)
                ),
                DiaryNote(
                    id = 2,
                    title = "Second Note",
                    content = "This is the second note.",
                    date = LocalDate.of(2023, 2, 2)
                ),
                DiaryNote(
                    id = 3,
                    title = "Third Note",
                    content = "This is the third note.",
                    date = LocalDate.of(2022, 3, 3)
                ),
                DiaryNote(
                    id = 4,
                    title = "Fourth Note",
                    content = "This is the fourth note.",
                    date = LocalDate.of(2021, 4, 4)
                ),
                DiaryNote(
                    id = 5,
                    title = "Fifth Note",
                    content = "This is the fifth note.",
                    date = LocalDate.of(2023, 5, 5)
                ),
                DiaryNote(
                    id = 6,
                    title = "Sixth Note",
                    content = "This is the sixth note.",
                    date = LocalDate.of(2022, 6, 6)
                ),
                DiaryNote(
                    id = 7,
                    title = "Seventh Note",
                    content = "This is the seventh note.",
                    date = LocalDate.of(2021, 7, 7)
                ),
                DiaryNote(
                    id = 8,
                    title = "Eighth Note",
                    content = "This is the eighth note.",
                    date = LocalDate.of(2023, 8, 8)
                ),
                DiaryNote(
                    id = 9,
                    title = "Ninth Note",
                    content = "This is the ninth note.",
                    date = LocalDate.of(2021, 9, 9)
                ),
                DiaryNote(
                    id = 10,
                    title = "Tenth Note",
                    content = "This is the tenth note.",
                    date = LocalDate.of(2022, 10, 10)
                ),
                DiaryNote(
                    id = 11,
                    title = "Eleventh Note",
                    content = "This is the eleventh note.",
                    date = LocalDate.of(2023, 11, 11)
                ),
                DiaryNote(
                    id = 12,
                    title = "Twelfth Note",
                    content = "This is the twelfth note.",
                    date = LocalDate.of(2022, 12, 12)
                )
            )

            val groupedByYear = notes.groupBy { it.date.year }.toSortedMap(compareByDescending { it })
            _yearSections.postValue(groupedByYear)
        }
    }
}