package com.example.petdiary.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petdiary.repository.DiaryNoteRepository
import com.example.petdiary.services.calendar.CastDates
import com.example.petdiary.ui.model.DiaryNote
import com.example.petdiary.ui.model.toDiaryNote
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val diaryNoteRepository = DiaryNoteRepository(application)

    private val _yearSections = MutableLiveData<Map<Int, List<DiaryNote>>>()
    val yearSections: LiveData<Map<Int, List<DiaryNote>>> = _yearSections

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            diaryNoteRepository.getAllNotes().collect { protoNotes ->
                val notes = protoNotes.map { protoDiaryNote ->
                    protoDiaryNote.toDiaryNote()
                }

                val groupedByYear = notes.groupBy {
                    CastDates.fromStringToDate(it.date).year
                }.toSortedMap(compareByDescending { it })
                _yearSections.postValue(groupedByYear)
            }
        }
    }
}