package com.example.petdiary.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petdiary.domain.model.DiaryNote
import com.example.petdiary.domain.model.Pet
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CreateNoteViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<DiaryNote>>().apply { value = mutableListOf() }
    val notes: LiveData<List<DiaryNote>> = _notes

    private val _pets = MutableLiveData<List<Pet>>().apply {
        value = mutableListOf(
            Pet("Buddy", "Dog", "Golden Retriever", LocalDate.now().minusYears(3)),
            Pet("Whiskers", "Cat", "Siamese", LocalDate.now().minusYears(2)),
            Pet("Tweety", "Bird", "Canary", LocalDate.now().minusYears(1))
        )
    }
    val pets: LiveData<List<Pet>> = _pets

    private var _date: LocalDate? = null
    val date: LocalDate?
        get() = _date

    fun setDate(date: LocalDate) {
        _date = date
    }

    fun getPetsNames(): Array<String>? {
        return _pets.value?.map { it.name }?.toTypedArray()
    }

    fun saveNote(note: DiaryNote) {
        viewModelScope.launch {
            _notes.value = _notes.value?.plus(note)
        }
    }
}