package app.cashadvisor.common.domain

import app.cashadvisor.common.utill.exceptions.NetworkException
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.logNetworkError
import java.net.ConnectException

abstract class ExceptionToErrorMapperBase {

    abstract fun <T> mapSpecificException(
        e: Exception,
    ): Resource<T>

    fun <T> mapException(e: Exception): Resource<T> {
        return when (e) {
            is ConnectException, is NetworkException -> {
                logNetworkError(e.message)
                getNetworkError(e)
            }

            else -> mapSpecificException(e)
        }
    }

    protected fun <T> getNetworkError(e: Exception): Resource<T> {
        return Resource.Error<T>(
            ErrorEntity.NetworksError.NoInternet(
                e.message ?: DEFAULT_ERROR_MESSAGE
            )
        )
    }

    protected fun <T> getUnknownError(e: Exception): Resource<T> {
        return Resource.Error(
            ErrorEntity.UnknownError(
                e.message ?: DEFAULT_ERROR_MESSAGE
            )
        )
    }

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "No error message"
    }
}

