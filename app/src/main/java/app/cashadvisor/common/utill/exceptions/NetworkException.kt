package app.cashadvisor.common.utill.exceptions

import java.io.IOException

sealed class NetworkException(
    message: String,
    httpStatusCode: Int,
) : IOException(message) {

    class InvalidOrExpiredToken(message: String, httpStatusCode: Int) :
        NetworkException(message, httpStatusCode)

    class Unauthorized(message: String, httpStatusCode: Int) :
        NetworkException(message, httpStatusCode)

    class InternalServerError(message: String, httpStatusCode: Int) :
        NetworkException(message, httpStatusCode)

    class BadRequest(message: String, httpStatusCode: Int) :
        NetworkException(message, httpStatusCode)

    class NoInternetConnection(
        message: String = NO_INTERNET_CONNECTION,
        httpStatusCode: Int = NO_INTERNET_CONNECTION_CODE
    ) :
        NetworkException(message, httpStatusCode)

    companion object {
        const val NO_INTERNET_CONNECTION = "No internet connection"
        const val NO_INTERNET_CONNECTION_CODE = -1
    }
}