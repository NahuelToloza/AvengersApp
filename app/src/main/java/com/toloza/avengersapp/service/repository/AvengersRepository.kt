package com.toloza.avengersapp.service.repository

import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.service.api.AvengersApi
import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.service.dto.EventsDto

class AvengersRepository(
    private val api: AvengersApi,
) {

    suspend fun getCharacters(page: Int = 0): Result<CharactersDto> {
        return api.getCharacters(page)
    }

    suspend fun getEvents(page: Int): Result<EventsDto> {
        return api.getEvents(page)
    }
}