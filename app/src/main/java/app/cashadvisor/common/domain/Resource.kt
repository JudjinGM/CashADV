package app.cashadvisor.common.domain

import app.cashadvisor.common.domain.model.ErrorEntity

sealed class Resource<T>(val data: T? = null, val error: ErrorEntity? = null) {

    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: ErrorEntity) : Resource<T>(error = error)

}