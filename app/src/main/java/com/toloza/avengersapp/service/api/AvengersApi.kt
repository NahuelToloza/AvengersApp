package com.toloza.avengersapp.service.api

import com.toloza.avengersapp.service.dto.CharactersDto
import retrofit2.http.GET
import retrofit2.http.Query

const val PUBLIC_KEY = "3a783b25c80e1c44875356dd363f272d"
const val TIME_STAMP = 1L
const val CHARACTER_PAGE_COUNT = 15

interface AvengersApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long = TIME_STAMP,
        @Query("apikey") publicApiKey: String = PUBLIC_KEY,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") itemsPerPage: Int = CHARACTER_PAGE_COUNT
    ) : CharactersDto
}