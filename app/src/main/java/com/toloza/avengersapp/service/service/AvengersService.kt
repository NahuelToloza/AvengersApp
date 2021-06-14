package com.toloza.avengersapp.service.service

import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.service.dto.EventsDto
import retrofit2.http.GET
import retrofit2.http.Query

const val PUBLIC_KEY = "3a783b25c80e1c44875356dd363f272d"
const val TIME_STAMP = 1L

const val CHARACTER_PAGE_COUNT = 15
const val MODIFIED_DESCENDANT = "-modified"

const val EVENTS_PAGE_COUNT = 25
const val START_DATE = "startDate"

interface AvengersService {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long = TIME_STAMP,
        @Query("apikey") publicApiKey: String = PUBLIC_KEY,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") itemsPerPage: Int = CHARACTER_PAGE_COUNT,
        @Query("orderBy") orderBy: String = MODIFIED_DESCENDANT
    ) : CharactersDto

    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("ts") ts: Long = TIME_STAMP,
        @Query("apikey") publicApiKey: String = PUBLIC_KEY,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") itemsPerPage: Int = EVENTS_PAGE_COUNT,
        @Query("orderBy") orderBy: String = START_DATE
    ) : EventsDto
}