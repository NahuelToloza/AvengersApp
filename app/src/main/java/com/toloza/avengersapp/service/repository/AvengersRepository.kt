package com.toloza.avengersapp.service.repository

import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.extensions.getGenericError
import com.toloza.avengersapp.service.api.AvengersApi
import com.toloza.avengersapp.service.api.CHARACTER_PAGE_COUNT
import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.service.error.HandleHttpErrors
import com.toloza.avengersapp.service.security.HashKeyBuilder

class AvengersRepository(
    private val api: AvengersApi,
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
                val result = api.getCharacters(hash = hash, offset = getOffset(page))
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