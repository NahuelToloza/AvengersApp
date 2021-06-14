package com.toloza.avengersapp.data.model.internal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicModel(
    val name: String,
    val year: String
): Parcelable