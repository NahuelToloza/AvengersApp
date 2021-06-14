package com.toloza.avengersapp.service.dto

import com.google.gson.annotations.SerializedName

class Comic(
    @SerializedName("items") val items: List<ComicItem>,
)

class ComicItem(
    @SerializedName("name") val name: String
)