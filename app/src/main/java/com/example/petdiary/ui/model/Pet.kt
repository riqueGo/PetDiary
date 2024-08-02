package com.example.petdiary.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.example.app.datastore.ProtoPet
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Pet(
    val name: String,
    val species: String,
    val race: String,
    val birthday: String
) : Parcelable