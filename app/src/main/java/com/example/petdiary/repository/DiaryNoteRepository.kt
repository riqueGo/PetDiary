package com.example.petdiary.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.app.datastore.ProtoDiaryNote
import com.example.app.datastore.ProtoDiaryNoteList
import com.example.petdiary.proto.DiaryNoteListSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiaryNoteRepository(val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<ProtoDiaryNoteList> by dataStore(
            fileName = "diary_notes.pb",
            serializer = DiaryNoteListSerializer
        )
    }

    suspend fun saveNote(note: ProtoDiaryNote) {
        context.dataStore.updateData { currentNotes ->
            currentNotes.toBuilder().addDiaryNotes(note).build()
        }
    }

    suspend fun deleteNote(noteId: Int) {
        context.dataStore.updateData { currentNotes ->
            val filteredNotes = currentNotes.diaryNotesList.filter { it.id != noteId }
            currentNotes.toBuilder().clearDiaryNotes().addAllDiaryNotes(filteredNotes).build()
        }
    }

    fun getAllNotes(): Flow<MutableList<ProtoDiaryNote>> {
        return context.dataStore.data.map { it.diaryNotesList }
    }
}