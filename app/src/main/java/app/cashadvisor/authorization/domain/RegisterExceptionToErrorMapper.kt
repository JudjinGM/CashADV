package app.cashadvisor.authorization.domain

import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.exceptions.RegisterException
import javax.inject.Inject

class RegisterExceptionToErrorMapper @Inject constructor() : BaseExceptionToErrorMapper() {

    override fun handleSpecificException(
        exception: Exception,
    ): ErrorEntity {
        return when (exception) {
            is RegisterException.Register -> handleRegisterException(exception)
            is RegisterException.EmailCodeConfirmation -> handleEmailConfirmCodeException(exception)
            else -> {
                handleUnknownError(exception)
            }
        }
    }

    private fun handleRegisterException(exception: RegisterException.Register): ErrorEntity {
        return when (exception) {
            is RegisterException.Register.FailedToGenerateTokenOrSendEmail -> {
                ErrorEntity.Register.FailedToGenerateTokenOrSendEmail(message = exception.message)
            }

            is RegisterException.Register.InvalidEmail -> {
                ErrorEntity.Register.InvalidEmail(message = exception.message)
            }
        }
    }

    private fun handleEmailConfirmCodeException(exception: RegisterException.EmailCodeConfirmation): ErrorEntity {
        return when (exception) {
            is RegisterException.EmailCodeConfirmation.InvalidToken -> {
                ErrorEntity.RegisterByEmailConfirmationWithCode.InvalidToken(exception.message)
            }

            is RegisterException.EmailCodeConfirmation.UnauthorizedWrongConfirmationCode ->
                ErrorEntity.RegisterByEmailConfirmationWithCode.WrongWithCodeConfirmationRegisterBy(
                    message = exception.message,
                    remainingAttempts = exception.remainingAttempts,
                    lockDuration = exception.lockDuration
                )

            is RegisterException.EmailCodeConfirmation.FailedToConfirmEmailOrRegisterUser -> {
                ErrorEntity.RegisterByEmailConfirmationWithCode.FailedToConfirmRegisterByEmailOrRegisterUserWithCode(
                    exception.message
                )
            }
        }
    }

}
