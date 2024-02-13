package app.cashadvisor.common.domain.model

sealed class ErrorEntity(open val message: String) {
    data class UnknownError(override val message: String = BLANC_ERROR) : ErrorEntity(message)

    sealed class NetworksError(override val message: String) : ErrorEntity(message) {
        data class NoInternet(override val message: String) : NetworksError(message)
    }

    sealed class Login(override val message: String) : ErrorEntity(message) {
        data class InvalidInput(override val message: String) : Login(message)
        data class InvalidEmailOrPassword(override val message: String) : Login(message)
        data class FailedToGenerateTokenOrSendEmail(override val message: String) :
            Login(message)
    }

    sealed class Register(override val message: String) : ErrorEntity(message) {
        data class InvalidEmail(override val message: String) : Register(message)
        data class FailedToGenerateTokenOrSendEmail(override val message: String) :
            Register(message)
    }


    sealed class LoginByEmailConfirmationWithCode(override val message: String) :
        ErrorEntity(message) {
        data class InvalidRequestPayload(override val message: String) :
            LoginByEmailConfirmationWithCode(message)

        data class WrongWithCodeConfirmationByEmail(
            override val message: String,
            val remainingAttempts: Int,
            val lockDuration: Int
        ) : LoginByEmailConfirmationWithCode(message)
    }

    sealed class RegisterByEmailConfirmationWithCode(override val message: String) :
        ErrorEntity(message) {
        data class InvalidToken(override val message: String) :
            RegisterByEmailConfirmationWithCode(message)

        data class WrongWithCodeConfirmationRegisterBy(
            override val message: String,
            val remainingAttempts: Int,
            val lockDuration: Int
        ) : RegisterByEmailConfirmationWithCode(message)

        data class FailedToConfirmRegisterByEmailOrRegisterUserWithCode(override val message: String) :
            RegisterByEmailConfirmationWithCode(message)

    }

    companion object {
        const val BLANC_ERROR = ""
    }

}
