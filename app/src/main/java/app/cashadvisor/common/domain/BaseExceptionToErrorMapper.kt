package app.cashadvisor.common.domain

import app.cashadvisor.common.utill.exceptions.NetworkException
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.extensions.logNetworkError
import java.net.ConnectException

abstract class BaseExceptionToErrorMapper {

    abstract fun <T> mapSpecificException(
        e: Exception,
    ): Resource<T>

    fun <T> mapException(e: Exception): Resource<T> {
        return when (e) {
            is ConnectException, is NetworkException -> {
                logNetworkError(e.message)
                getNetworkError(e)
            }

            else -> {
                logNetworkError(e.message)
                mapSpecificException(e)
            }
        }
    }

    private fun <T> getNetworkError(e: Exception): Resource<T> {
        return Resource.Error(
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

