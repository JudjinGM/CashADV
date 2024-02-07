package app.cashadvisor.authorization.data

import app.cashadvisor.authorization.data.models.response.customError.ErrorWrongConfirmationCodeResponse
import app.cashadvisor.common.data.models.ErrorResponse
import app.cashadvisor.common.utill.exceptions.AuthException
import app.cashadvisor.common.utill.exceptions.NetworkException
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NetworkToAuthExceptionMapper @Inject constructor(
    private val json: Json
) {

    fun getExceptionForLogin(e: NetworkException): AuthException {
        return when (e) {
            is NetworkException.BadRequest -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.Login.InvalidInputOrContentType(
                    message = errorResponse.message,
                    statusCode = errorResponse.statusCode
                )
            }

            is NetworkException.Unauthorized -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.Login.InvalidEmailOrPassword(
                    message = errorResponse.message,
                    errorResponse.statusCode
                )
            }

            is NetworkException.InternalServerError -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.Login.FailedToGenerateTokenOrSendEmail(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            else -> mapCommonException(e)

        }
    }

    fun getExceptionForLoginCodeConfirmation(e: NetworkException): AuthException {
        return when (e) {
            is NetworkException.BadRequest -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.LoginCodeConfirmation.InvalidRequestPayload(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            is NetworkException.Unauthorized -> {
                val errorResponse =
                    getErrorResponse<ErrorWrongConfirmationCodeResponse>(e.errorBody)
                AuthException.LoginCodeConfirmation.UnauthorizedWrongConfirmationCode(
                    remainingAttempts = errorResponse.remainingAttempts,
                    lockDuration = errorResponse.lockDuration,
                    message = errorResponse.error,
                    statusCode = errorResponse.statusCode
                )
            }

            else -> mapCommonException(e)
        }
    }


    fun getExceptionForRegister(e: NetworkException): AuthException {
        return when (e) {
            is NetworkException.BadRequest -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.Register.InvalidEmail(
                    message = errorResponse.message,
                    errorResponse.statusCode
                )
            }

            is NetworkException.InternalServerError -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.Register.FailedToGenerateTokenOrSendEmail(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            else -> mapCommonException(e)

        }
    }

    fun getExceptionForEmailCodeConfirmation(e: NetworkException): AuthException {
        return when (e) {
            is NetworkException.BadRequest -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.EmailCodeConfirmation.InvalidToken(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            is NetworkException.Unauthorized -> {
                val errorResponse =
                    getErrorResponse<ErrorWrongConfirmationCodeResponse>(e.errorBody)
                AuthException.EmailCodeConfirmation.UnauthorizedWrongConfirmationCode(
                    remainingAttempts = errorResponse.remainingAttempts,
                    lockDuration = errorResponse.lockDuration,
                    message = errorResponse.error,
                    statusCode = errorResponse.statusCode
                )
            }

            is NetworkException.InternalServerError -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.EmailCodeConfirmation.FailedToConfirmEmailOrRegisterUser(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            else -> mapCommonException(e)
        }
    }

    private fun mapCommonException(e: NetworkException): AuthException {
        return when (e) {
            is NetworkException.NoInternetConnection -> {
                val errorResponse = getErrorResponse<ErrorResponse>(e.errorBody)
                AuthException.NoConnection(errorResponse.message)
            }

            is NetworkException.Undefined -> {
                AuthException.Undefined()
            }

            else -> {
                AuthException.Undefined()
            }
        }
    }

    private inline fun <reified T> getErrorResponse(errorMessage: String): T {
        try {
            return json.decodeFromString<T>(errorMessage)

        } catch (e: Exception) {
            throw AuthException.Undefined()
        }
    }

}