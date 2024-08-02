package com.example.petdiary.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.example.app.datastore.ProtoDiaryNote
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryNote(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val pets: List<Pet>,
    val images: List<String>
) : Parcelable