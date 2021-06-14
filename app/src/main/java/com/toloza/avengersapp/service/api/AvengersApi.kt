package com.toloza.avengersapp.service.api

import com.toloza.avengersapp.BuildConfig
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.extensions.getGenericError
import com.toloza.avengersapp.service.AvengersService
import com.toloza.avengersapp.service.CHARACTER_PAGE_COUNT
import com.toloza.avengersapp.service.EVENTS_PAGE_COUNT
import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.service.dto.EventsDto
import com.toloza.avengersapp.service.error.HandleHttpErrors
import com.toloza.avengersapp.service.security.HashKeyBuilder

class AvengersApi(
    private val service: AvengersService,
    private val hashKeyBuilder: HashKeyBuilder,
    private val handleHttpErrors: HandleHttpErrors
) {

    private val hashKey: String? = null
        get() = field ?: run {
            hashKeyBuilder.build()
        }

    private val publicKey: String
        get() = BuildConfig.PUBLIC_KEY_AVENGERS_API

    suspend fun getCharacters(page: Int = 0): Result<CharactersDto> {
        val hash = hashKey
        hash?.let {
            try {
                val result = service.getCharacters(
                    hash = hash,
                    offset = getOffset(page, CHARACTER_PAGE_COUNT),
                    publicApiKey = publicKey
                )
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
                val result = service.getEvents(
                    hash = hash,
                    offset = getOffset(page, EVENTS_PAGE_COUNT),
                    publicApiKey = publicKey
                )
                return Result.Success(result)
            } catch (exception: Exception) {
                handleHttpErrors.handleApiException(exception)
            }
            return handleHttpErrors.handleApiException(Exception(""))
        } ?: run {
            return Result.Error((getGenericError("There was a problem to generate hash")))
        }
    }

    private fun getOffset(page: Int, pageCount: Int): Int {
        return page * pageCount
    }
}
