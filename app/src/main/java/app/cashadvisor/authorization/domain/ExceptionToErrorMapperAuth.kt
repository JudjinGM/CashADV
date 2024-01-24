package app.cashadvisor.authorization.domain

import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.ExceptionToErrorMapperBase
import app.cashadvisor.common.utill.exceptions.AuthException
import app.cashadvisor.common.utill.extensions.logDebugError

class ExceptionToErrorMapperAuth : ExceptionToErrorMapperBase() {

    override fun <T> mapSpecificException(
        e: Exception,
    ): Resource<T> {
        return when (e) {
            is AuthException -> when (e) {
                is AuthException.InvalidEmailOrPassword ->
                    Resource.Error(
                        ErrorEntity.AuthError.InvalidEmailOrPassword(
                            e.message ?: DEFAULT_ERROR_MESSAGE
                        )
                    )
            }

            else -> {
                logDebugError(e.message)
                getUnknownError(e)
            }
        }
    }

}