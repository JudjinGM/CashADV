package app.cashadvisor.common.utill.exceptions

import java.io.IOException

sealed class AuthException(
    message: String,
    httpStatusCode: Int,
) : IOException(message) {
    class InvalidEmailOrPassword(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)
}
