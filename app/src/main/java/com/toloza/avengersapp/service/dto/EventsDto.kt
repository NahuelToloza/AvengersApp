package com.toloza.avengersapp.service.dto

import com.google.gson.annotations.SerializedName

class EventsDto(
    @SerializedName("data") val data: DataEvent
)

class DataEvent(
    @SerializedName("results") val results: List<Event>
)

class Event(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("comics") val comics: Comic,
    @SerializedName("thumbnail") val thumbnail: Thumbnail
)

class Comic(
    @SerializedName("items") val items: List<ComicItem>,
)

class ComicItem(
    @SerializedName("name") val name: String
)