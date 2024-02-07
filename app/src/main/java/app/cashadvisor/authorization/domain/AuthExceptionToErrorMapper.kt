package app.cashadvisor.authorization.domain

import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.exceptions.AuthException

class AuthExceptionToErrorMapper : BaseExceptionToErrorMapper() {

    override fun <T> mapSpecificException(
        e: Exception,
    ): Resource<T> {
        return when (e) {
            is AuthException.Login -> handleLoginException(e)
            is AuthException.Register -> handleRegisterException(e)
            is AuthException.EmailCodeConfirmation -> handleEmailConfirmCodeException(e)
            is AuthException.LoginCodeConfirmation -> handleLoginConfirmCodeException(e)
            else -> {
                getUnknownError(e)
            }
        }
    }

    private fun <T> handleLoginException(e: AuthException.Login): Resource<T> {
        return when (e) {
            is AuthException.Login.FailedToGenerateTokenOrSendEmail -> {
                Resource.Error(
                    ErrorEntity.Auth.Login.FailedToGenerateTokenOrSendEmail(
                        e.message
                    )
                )
            }

            is AuthException.Login.InvalidEmailOrPassword -> {
                Resource.Error(
                    ErrorEntity.Auth.Login.InvalidEmailOrPassword(
                        e.message
                    )
                )
            }

            is AuthException.Login.InvalidInputOrContentType -> {
                Resource.Error(
                    ErrorEntity.Auth.Login.InvalidInput(
                        e.message
                    )
                )
            }
        }
    }

    private fun <T> handleRegisterException(e: AuthException.Register): Resource<T> {
        return when (e) {
            is AuthException.Register.FailedToGenerateTokenOrSendEmail -> {
                Resource.Error(
                    ErrorEntity.Auth.Register.FailedToGenerateTokenOrSendEmail(message = e.message)
                )
            }

            is AuthException.Register.InvalidEmail -> {
                Resource.Error(
                    ErrorEntity.Auth.Register.InvalidEmail(message = e.message)
                )
            }
        }
    }

    private fun <T> handleLoginConfirmCodeException(e: AuthException.LoginCodeConfirmation): Resource<T> {
        return when (e) {
            is AuthException.LoginCodeConfirmation.InvalidRequestPayload -> {
                Resource.Error(
                    ErrorEntity.Auth.LoginCodeConfirmation.InvalidRequestPayload(e.message)
                )
            }

            is AuthException.LoginCodeConfirmation.UnauthorizedWrongConfirmationCode -> {
                Resource.Error(
                    ErrorEntity.Auth.LoginCodeConfirmation.WrongConfirmationCode(
                        message = e.message,
                        remainingAttempts = e.remainingAttempts,
                        lockDuration = e.lockDuration
                    )
                )
            }
        }
    }

    private fun <T> handleEmailConfirmCodeException(e: AuthException.EmailCodeConfirmation): Resource<T> {
        return when (e) {
            is AuthException.EmailCodeConfirmation.InvalidToken -> {
                Resource.Error(
                    ErrorEntity.Auth.EmailCodeConfirmation.InvalidToken(e.message)
                )
            }

            is AuthException.EmailCodeConfirmation.UnauthorizedWrongConfirmationCode -> Resource.Error(
                ErrorEntity.Auth.EmailCodeConfirmation.WrongConfirmationCode(
                    message = e.message,
                    remainingAttempts = e.remainingAttempts,
                    lockDuration = e.lockDuration
                )
            )

            is AuthException.EmailCodeConfirmation.FailedToConfirmEmailOrRegisterUser -> {
                Resource.Error(
                    ErrorEntity.Auth.EmailCodeConfirmation.FailedToConfirmEmailOrRegisterUser(e.message)
                )
            }
        }
    }
}
