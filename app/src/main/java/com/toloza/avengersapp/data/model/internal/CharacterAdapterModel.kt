package com.toloza.avengersapp.data.model.internal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterAdapterModel(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val description: String
): Parcelable