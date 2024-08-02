package com.example.petdiary.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.app.datastore.DiaryNote
import com.example.app.datastore.DiaryNoteList
import com.example.petdiary.proto.DiaryNoteListSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiaryNoteRepository(val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<DiaryNoteList> by dataStore(
            fileName = "diary_notes.pb",
            serializer = DiaryNoteListSerializer
        )
    }

    suspend fun saveNote(note: DiaryNote) {
        context.dataStore.updateData { currentNotes ->
            currentNotes.toBuilder().addDiaryNotes(note).build()
        }
    }

    fun getAllNotes(): Flow<MutableList<DiaryNote>> {
        return context.dataStore.data.map { it.diaryNotesList }
    }
}