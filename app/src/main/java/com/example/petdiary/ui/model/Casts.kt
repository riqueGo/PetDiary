package com.example.petdiary.ui.model

import com.example.app.datastore.ProtoDiaryNote
import com.example.app.datastore.ProtoPet

fun ProtoDiaryNote.toDiaryNote(): DiaryNote {
    return DiaryNote(
        id = id,
        title = title,
        content = content,
        date = date,
        pets = petsList.map { it.toPet() },
        images = imagesList.toList()
    )
}

fun ProtoPet.toPet(): Pet {
    return Pet(
        name = name,
        species = species,
        race = race,
        birthday = birthday
    )
}