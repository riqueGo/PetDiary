package com.example.petdiary.mock

import com.example.app.datastore.ProtoDiaryNote
import com.example.app.datastore.ProtoPet
import com.example.petdiary.repository.DiaryNoteRepository

suspend fun addMockData(diaryNoteRepository: DiaryNoteRepository) {
    val mockNotes = listOf(
        ProtoDiaryNote.newBuilder()
            .setId(System.currentTimeMillis().toInt())  // Casting ID to Int
            .setTitle("Mock Note 1")
            .setContent("This is a very large mock note content 1. ".repeat(20)) // Large content
            .setDate("2021-01-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Fluffy").setSpecies("Dog").setRace("Golden Retriever").setBirthday("2020-05-01").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 1).toInt())
            .setTitle("Mock Note 2")
            .setContent("This is a very large mock note content 2. ".repeat(20))
            .setDate("2022-02-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Whiskers").setSpecies("Cat").setRace("Siamese").setBirthday("2018-03-10").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 2).toInt())
            .setTitle("Mock Note 3")
            .setContent("This is a very large mock note content 3. ".repeat(20))
            .setDate("2023-03-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Spike").setSpecies("Dog").setRace("Bulldog").setBirthday("2019-07-15").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 3).toInt())
            .setTitle("Mock Note 4")
            .setContent("This is a very large mock note content 4. ".repeat(20))
            .setDate("2024-04-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Bella").setSpecies("Bird").setRace("Parrot").setBirthday("2021-01-20").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 4).toInt())
            .setTitle("Mock Note 5")
            .setContent("This is a very large mock note content 5. ".repeat(20))
            .setDate("2021-05-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Oscar").setSpecies("Fish").setRace("Goldfish").setBirthday("2023-08-12").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 5).toInt())
            .setTitle("Mock Note 6")
            .setContent("This is a very large mock note content 6. ".repeat(20))
            .setDate("2022-06-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Max").setSpecies("Dog").setRace("Beagle").setBirthday("2020-09-25").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 6).toInt())
            .setTitle("Mock Note 7")
            .setContent("This is a very large mock note content 7. ".repeat(20))
            .setDate("2023-07-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Mittens").setSpecies("Cat").setRace("Persian").setBirthday("2019-02-14").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 7).toInt())
            .setTitle("Mock Note 8")
            .setContent("This is a very large mock note content 8. ".repeat(20))
            .setDate("2024-08-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Coco").setSpecies("Bird").setRace("Canary").setBirthday("2022-06-30").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 8).toInt())
            .setTitle("Mock Note 9")
            .setContent("This is a very large mock note content 9. ".repeat(20))
            .setDate("2021-09-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Buddy").setSpecies("Dog").setRace("Labrador").setBirthday("2021-11-05").build())
            .build(),

        ProtoDiaryNote.newBuilder()
            .setId((System.currentTimeMillis() + 9).toInt())
            .setTitle("Mock Note 10")
            .setContent("This is a very large mock note content 10. ".repeat(20))
            .setDate("2022-10-01")
            .addImages("content://media/external/images/media/109545")
            .addPets(ProtoPet.newBuilder().setName("Nemo").setSpecies("Fish").setRace("Clownfish").setBirthday("2024-01-01").build())
            .build()
    )

    mockNotes.forEach { note ->
        diaryNoteRepository.saveNote(note)
    }
}