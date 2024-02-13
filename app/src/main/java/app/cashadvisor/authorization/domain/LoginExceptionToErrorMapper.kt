package app.cashadvisor.authorization.domain

import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.exceptions.LoginException
import javax.inject.Inject

class LoginExceptionToErrorMapper @Inject constructor() : BaseExceptionToErrorMapper() {

    override fun handleSpecificException(
        exception: Exception,
    ): ErrorEntity {
        return when (exception) {
            is LoginException.Login -> handleLoginException(exception)
            is LoginException.LoginCodeConfirmation -> handleLoginConfirmCodeException(exception)
            else -> {
                handleUnknownError(exception)
            }
        }
    }

    private fun handleLoginException(exception: LoginException.Login): ErrorEntity {
        return when (exception) {
            is LoginException.Login.FailedToGenerateTokenOrSendEmail -> {
                ErrorEntity.Login.FailedToGenerateTokenOrSendEmail(
                    exception.message
                )
            }

            is LoginException.Login.InvalidEmailOrPassword -> {
                ErrorEntity.Login.InvalidEmailOrPassword(
                    exception.message
                )
            }

            is LoginException.Login.InvalidInputOrContentType -> {
                ErrorEntity.Login.InvalidInput(
                    exception.message
                )
            }
        }
    }

    private fun handleLoginConfirmCodeException(exception: LoginException.LoginCodeConfirmation): ErrorEntity {
        return when (exception) {
            is LoginException.LoginCodeConfirmation.InvalidRequestPayload -> {
                ErrorEntity.LoginByEmailConfirmationWithCode.InvalidRequestPayload(exception.message)
            }

            is LoginException.LoginCodeConfirmation.UnauthorizedWrongConfirmationCode -> {
                ErrorEntity.LoginByEmailConfirmationWithCode.WrongWithCodeConfirmationByEmail(
                    message = exception.message,
                    remainingAttempts = exception.remainingAttempts,
                    lockDuration = exception.lockDuration
                )
            }
        }
    }

}
