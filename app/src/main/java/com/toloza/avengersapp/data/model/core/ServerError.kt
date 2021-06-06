package com.toloza.avengersapp.data.model.core

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ServerError(
    @SerializedName("code") var code: Long = 0,
    @SerializedName("message") var message: String = "",
    @SerializedName("extra") var extra: Map<String, Any>? = null
): Serializable

enum class ServerErrorCode constructor(val value: Long) {
    InvalidGeneric(13)
}