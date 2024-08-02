package com.example.petdiary.proto

import androidx.datastore.core.Serializer
import com.example.app.datastore.ProtoDiaryNoteList
import java.io.InputStream
import java.io.OutputStream

object DiaryNoteListSerializer : Serializer<ProtoDiaryNoteList> {
    override val defaultValue: ProtoDiaryNoteList = ProtoDiaryNoteList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoDiaryNoteList {
        return ProtoDiaryNoteList.parseFrom(input)
    }

    override suspend fun writeTo(t: ProtoDiaryNoteList, output: OutputStream) {
        t.writeTo(output)
    }
}