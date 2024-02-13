package app.cashadvisor.common.utill.exceptions

import java.io.IOException


sealed class LoginException(
    override val message: String
) : IOException(message) {

    data class NoConnection(
        override val message: String = NO_INTERNET_CONNECTION
    ) : LoginException(message)

    data class Undefined(override val message: String = UNDEFINED_MESSAGE) : LoginException(message)

    sealed class Login(
        message: String
    ) : LoginException(message) {

        class InvalidInputOrContentType(
            override val message: String,
            val statusCode: Int
        ) : Login(message)

        class InvalidEmailOrPassword(
            override val message: String,
            val statusCode: Int
        ) : Login(message)

        class FailedToGenerateTokenOrSendEmail(
            override val message: String,
            val statusCode: Int
        ) : Login(message)

    }

    sealed class LoginCodeConfirmation(
        override val message: String,
    ) : LoginException(message) {
        class InvalidRequestPayload(
            override val message: String,
            val statusCode: Int
        ) : LoginCodeConfirmation(message)

        class UnauthorizedWrongConfirmationCode(
            override val message: String,
            val remainingAttempts: Int,
            val lockDuration: Int,
            val statusCode: Int
        ) : LoginCodeConfirmation(message)
    }

    companion object {
        const val NO_INTERNET_CONNECTION = "No internet connection"
        const val UNDEFINED_MESSAGE = "Undefined"
    }
}
