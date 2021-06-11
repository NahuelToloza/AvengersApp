package com.toloza.avengersapp.service.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class CharactersDto(
    @SerializedName("data") val data: Data
)

data class Data(
    @SerializedName("results") val results: List<Character>
)

data class Character(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: Date,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail
)

data class Thumbnail(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)