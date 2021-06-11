package com.toloza.avengersapp.service.error

import android.content.Context
import androidx.annotation.StringRes
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.data.model.core.ServerErrorCode
import com.toloza.avengersapp.extensions.getGenericError
import retrofit2.HttpException
import java.net.ConnectException

class HandleHttpErrors(private val context: Context) {

    fun handleApiException(exception: Exception): Result.Error {
        val error = when (exception) {
            is HttpException -> {
                handleHttpException(exception.code())
            }
            is ConnectException -> {
                ServerError(
                    ServerErrorCode.ServiceUnavailable.code,
                    getText(R.string.error_bad_connection)
                )
            }
            else -> {
                getGenericError(getText(R.string.generic_error))
            }
        }
        return Result.Error(error)
    }

    private fun handleHttpException(code: Int): ServerError {
        return when (code) {
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
    }

    private fun getText(@StringRes stringRes: Int): String = context.getString(stringRes)
}
