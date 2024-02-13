package app.cashadvisor.common.utill.exceptions

import java.io.IOException


sealed class RegisterException(
    override val message: String
) : IOException(message) {

    data class NoConnection(
        override val message: String = NO_INTERNET_CONNECTION
    ) : RegisterException(message)

    data class Undefined(override val message: String = UNDEFINED_MESSAGE) :
        RegisterException(message)

    sealed class Register(
        message: String
    ) : RegisterException(message) {

        class InvalidEmail(
            override val message: String,
            val statusCode: Int
        ) : Register(message)

        class FailedToGenerateTokenOrSendEmail(
            override val message: String,
            val statusCode: Int
        ) : Register(message)
    }

    sealed class EmailCodeConfirmation(
        override val message: String,
    ) : RegisterException(message) {
        class InvalidToken(
            override val message: String,
            val statusCode: Int
        ) : EmailCodeConfirmation(message)

        class UnauthorizedWrongConfirmationCode(
            override val message: String,
            val remainingAttempts: Int,
            val lockDuration: Int,
            val statusCode: Int
        ) : EmailCodeConfirmation(message)

        class FailedToConfirmEmailOrRegisterUser(
            override val message: String,
            val statusCode: Int
        ) : EmailCodeConfirmation(message)
    }


    companion object {
        const val NO_INTERNET_CONNECTION = "No internet connection"
        const val UNDEFINED_MESSAGE = "Undefined"
    }
}
