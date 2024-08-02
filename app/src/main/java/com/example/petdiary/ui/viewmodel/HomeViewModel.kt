package com.example.petdiary.ui.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app.datastore.DiaryNote
import com.example.petdiary.repository.DiaryNoteRepository
import com.example.petdiary.services.calendar.CastDates
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val diaryNoteRepository = DiaryNoteRepository(application)

    private val _yearSections = MutableLiveData<Map<Int, List<DiaryNote>>>()
    val yearSections: LiveData<Map<Int, List<DiaryNote>>> = _yearSections

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            diaryNoteRepository.getAllNotes().collect { notes ->
                val groupedByYear = notes.groupBy {
                    CastDates.fromStringToDate(it.date).year
                }.toSortedMap(compareByDescending { it })
                _yearSections.postValue(groupedByYear)
            }
        }
    }
}