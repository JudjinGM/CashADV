package app.cashadvisor

sealed class AuthException(
    message: String,
    httpStatusCode: Int,
) : java.io.IOException(message) {

    class AccessTokenExceptionOne(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)

    class AccessTokenExceptionTwo(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)

    class AccessTokenExceptionThree(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)

    class RefreshTokenExceptionOne(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)

    class RefreshTokenExceptionTwo(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)

    class RefreshTokenExceptionThree(message: String, httpStatusCode: Int) :
        AuthException(message, httpStatusCode)
}