package app.cashadvisor

sealed class Resource<T, R>(val data: T? = null, val error: R? = null) {
    class Success<T, R>(data: T) : Resource<T, R>(data)
    class Error<T, R>(data: T? = null, error: R) : Resource<T, R>(data, error)
}