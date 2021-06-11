package com.toloza.avengersapp.service.error

import android.content.Context
import androidx.annotation.StringRes
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.data.model.core.ServerErrorCode
import com.toloza.avengersapp.extensions.getGenericError

class HandleHttpErrors(private val context: Context) {
    fun handleHttpException(code: Int): Result.Error {
        val error = when (code) {
            ServerErrorCode.BadRequest.code -> {
                ServerError(ServerErrorCode.BadRequest.code, getText(R.string.error_bad_request))
            }
            ServerErrorCode.Forbidden.code -> {
                ServerError(ServerErrorCode.Forbidden.code, getText(R.string.error_forbidden))
            }
            ServerErrorCode.RequestTimeOut.code -> {
                ServerError(
                    ServerErrorCode.RequestTimeOut.code,
                    getText(R.string.error_request_time_out)
                )
            }
            ServerErrorCode.InternalServerError.code -> {
                ServerError(
                    ServerErrorCode.InternalServerError.code,
                    getText(R.string.error_internal_server_error)
                )
            }
            else -> {
                getGenericError(getText(R.string.generic_error))
            }
        }
        return Result.Error(error)
    }

    private fun getText(@StringRes stringRes: Int): String = context.getString(stringRes)
}
