package com.toloza.avengersapp.service.api

import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.extensions.getGenericError
import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.service.dto.EventsDto
import com.toloza.avengersapp.service.error.HandleHttpErrors
import com.toloza.avengersapp.service.security.HashKeyBuilder
import com.toloza.avengersapp.service.service.AvengersService
import com.toloza.avengersapp.service.service.CHARACTER_PAGE_COUNT

class AvengersApi(
    private val service: AvengersService,
    private val hashKeyBuilder: HashKeyBuilder,
    private val handleHttpErrors: HandleHttpErrors
) {

    private val hashKey: String? = null
        get() = field ?: run {
            hashKeyBuilder.build()
        }

    suspend fun getCharacters(page: Int = 0): Result<CharactersDto> {
        val hash = hashKey
        hash?.let {
            try {
                val result = service.getCharacters(hash = hash, offset = getOffset(page))
                return Result.Success(result)
            } catch (exception: Exception) {
                handleHttpErrors.handleApiException(exception)
            }
            return handleHttpErrors.handleApiException(Exception(""))
        } ?: run {
            return Result.Error((getGenericError("There was a problem to generate hash")))
        }
    }

    suspend fun getEvents(page: Int = 0): Result<EventsDto> {
        val hash = hashKey
        hash?.let {
            try {
                val result = service.getEvents(hash = hash, offset = getOffset(page))
                return Result.Success(result)
            } catch (exception: Exception) {
                handleHttpErrors.handleApiException(exception)
            }
            return handleHttpErrors.handleApiException(Exception(""))
        } ?: run {
            return Result.Error((getGenericError("There was a problem to generate hash")))
        }
    }

    private fun getOffset(page: Int): Int {
        return page * CHARACTER_PAGE_COUNT
    }
}
