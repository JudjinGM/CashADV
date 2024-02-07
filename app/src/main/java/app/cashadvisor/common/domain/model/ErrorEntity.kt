package app.cashadvisor.common.domain.model

import app.cashadvisor.authorization.domain.models.ConfirmCodeValidationError
import app.cashadvisor.authorization.domain.models.EmailValidationError
import app.cashadvisor.authorization.domain.models.PasswordValidationError

sealed class ErrorEntity(open val message: String) {
    data class UnknownError(override val message: String) : ErrorEntity(message)

    sealed class NetworksError(override val message: String) : ErrorEntity(message) {
        data class NoInternet(override val message: String) : NetworksError(message)
    }

    sealed class Auth(override val message: String) : ErrorEntity(message) {
        sealed class Login(override val message: String) : Auth(message) {
            data class InvalidInput(override val message: String) : Login(message)
            data class InvalidEmailOrPassword(override val message: String) : Login(message)
            data class FailedToGenerateTokenOrSendEmail(override val message: String) :
                Login(message)

            data class InvalidEmailInput(val emailValidationError: EmailValidationError) :
                Login(emailValidationError.name)

            data class InvalidPasswordInput(val passwordValidationError: PasswordValidationError) :
                Login(passwordValidationError.name)
        }

        sealed class Register(override val message: String) : Auth(message) {
            data class InvalidEmail(override val message: String) : Register(message)
            data class FailedToGenerateTokenOrSendEmail(override val message: String) :
                Register(message)

            data class InvalidEmailInput(val emailValidationError: EmailValidationError) :
                Register(emailValidationError.name)

            data class InvalidPasswordInput(val passwordValidationError: PasswordValidationError) :
                Register(passwordValidationError.name)
        }


        sealed class LoginCodeConfirmation(override val message: String) : Auth(message) {
            data class InvalidRequestPayload(override val message: String) :
                LoginCodeConfirmation(message)

            data class WrongConfirmationCode(
                override val message: String,
                val remainingAttempts: Int,
                val lockDuration: Int
            ) : LoginCodeConfirmation(message)

            data class InvalidCodeInput(val codeError: ConfirmCodeValidationError) :
                LoginCodeConfirmation(message = codeError.name)

            data class InvalidEmailInput(val emailValidationError: EmailValidationError) :
                LoginCodeConfirmation(emailValidationError.name)
        }

        sealed class EmailCodeConfirmation(override val message: String) : Auth(message) {
            data class InvalidToken(override val message: String) : EmailCodeConfirmation(message)
            data class WrongConfirmationCode(
                override val message: String,
                val remainingAttempts: Int,
                val lockDuration: Int
            ) : EmailCodeConfirmation(message)

            data class FailedToConfirmEmailOrRegisterUser(override val message: String) :
                EmailCodeConfirmation(message)

            data class InvalidCodeInput(val codeError: ConfirmCodeValidationError) :
                EmailCodeConfirmation(message = codeError.name)

            data class InvalidEmailInput(val emailValidationError: EmailValidationError) :
                EmailCodeConfirmation(emailValidationError.name)

        }
    }
}