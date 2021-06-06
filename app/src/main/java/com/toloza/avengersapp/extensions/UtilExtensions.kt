package com.toloza.avengersapp.extensions

import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.data.model.core.ServerErrorCode

fun getGenericError(message: String): ServerError {
    return ServerError(ServerErrorCode.InvalidGeneric.value, message)
}