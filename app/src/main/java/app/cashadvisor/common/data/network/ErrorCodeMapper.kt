package app.cashadvisor.common.data.network

import app.cashadvisor.common.utill.exceptions.AuthException
import app.cashadvisor.common.utill.exceptions.NetworkException
import app.cashadvisor.common.utill.extensions.logNetworkError
import java.io.IOException
import javax.inject.Inject

class ErrorCodeMapper @Inject constructor() {

    fun getException(message: String, responseCode: Int, statusCode: Int): IOException {
        logNetworkError(responseCode.toString())
        var result: IOException = when (responseCode) {
            400 -> NetworkException.BadRequest(message, statusCode)
            401 -> NetworkException.Unauthorized(message, statusCode)
            500 -> NetworkException.InternalServerError(message, statusCode)
            else -> NetworkException.Undefined(message, statusCode)
        }

        //с сервера должен приходить уникальный код ошибки (statusCode), а не повтор responseCode
        when (statusCode) {
            401 -> result = AuthException.InvalidEmailOrPassword(message, statusCode)
        }
        return result
    }
}