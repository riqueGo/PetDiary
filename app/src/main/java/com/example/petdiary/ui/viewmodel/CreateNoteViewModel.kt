package com.example.petdiary.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app.datastore.DiaryNote
import com.example.petdiary.ui.model.Pet
import com.example.petdiary.repository.DiaryNoteRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CreateNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val diaryNoteRepository = DiaryNoteRepository(application)

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

    private val _selectedImages = MutableLiveData<List<Uri>>().apply { value = mutableListOf() }
    val selectedImages: LiveData<List<Uri>> = _selectedImages

    fun setDate(date: LocalDate) {
        _date = date
    }

    fun getPetsNames(): Array<String>? {
        return _pets.value?.map { it.name }?.toTypedArray()
    }

    fun addImage(uri: Uri) {
        val currentImages = _selectedImages.value?.toMutableList() ?: mutableListOf()
        if (currentImages.size < 10) {
            currentImages.add(uri)
            _selectedImages.value = currentImages
        }
    }

    fun clearImages() {
        _selectedImages.value = emptyList()
    }

    fun saveNote(note: DiaryNote) {
        viewModelScope.launch {
            diaryNoteRepository.saveNote(note)
        }
    }
}
