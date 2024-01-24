package app.cashadvisor.common.domain.model

sealed class ErrorEntity(open val message: String) {
    sealed class NetworksError(override val message: String) : ErrorEntity(message) {
        data class NoInternet(override val message: String) : NetworksError(message)
    }

    sealed class AuthError(override val message: String) : ErrorEntity(message) {
        class InvalidEmailOrPassword(message: String) : NetworksError(message)

    }

    // сюда же можно добавить FileError, для обработки ошибок при работе с файлами и т.д.

    class UnknownError(message: String) : ErrorEntity(message)
}