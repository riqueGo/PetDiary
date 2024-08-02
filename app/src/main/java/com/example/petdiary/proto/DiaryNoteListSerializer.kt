package com.example.petdiary.proto

import androidx.datastore.core.Serializer
import com.example.app.datastore.DiaryNoteList
import java.io.InputStream
import java.io.OutputStream

object DiaryNoteListSerializer : Serializer<DiaryNoteList> {
    override val defaultValue: DiaryNoteList = DiaryNoteList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DiaryNoteList {
        return DiaryNoteList.parseFrom(input)
    }

    override suspend fun writeTo(t: DiaryNoteList, output: OutputStream) {
        t.writeTo(output)
    }
}