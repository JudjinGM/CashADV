package app.cashadvisor.authorization.data

import app.cashadvisor.authorization.data.models.response.customError.ErrorWrongConfirmationCodeResponse
import app.cashadvisor.common.data.models.ErrorResponse
import app.cashadvisor.common.utill.exceptions.LoginException
import app.cashadvisor.common.utill.exceptions.NetworkException
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NetworkToLoginExceptionMapper @Inject constructor(
    private val json: Json
) {

    fun handleExceptionForLoginByEmail(exception: NetworkException): LoginException {
        return when (exception) {
            is NetworkException.BadRequest -> {
                val errorResponse = handleErrorResponse<ErrorResponse>(exception.errorBody)
                LoginException.Login.InvalidInputOrContentType(
                    message = errorResponse.message,
                    statusCode = errorResponse.statusCode
                )
            }

            is NetworkException.Unauthorized -> {
                val errorResponse = handleErrorResponse<ErrorResponse>(exception.errorBody)
                LoginException.Login.InvalidEmailOrPassword(
                    message = errorResponse.message,
                    errorResponse.statusCode
                )
            }

            is NetworkException.InternalServerError -> {
                val errorResponse = handleErrorResponse<ErrorResponse>(exception.errorBody)
                LoginException.Login.FailedToGenerateTokenOrSendEmail(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            else -> handleCommonException(exception)

        }
    }

    fun handleExceptionForLoginConfirmationByEmailWithCode(exception: NetworkException): LoginException {
        return when (exception) {
            is NetworkException.BadRequest -> {
                val errorResponse = handleErrorResponse<ErrorResponse>(exception.errorBody)
                LoginException.LoginCodeConfirmation.InvalidRequestPayload(
                    errorResponse.message,
                    errorResponse.statusCode
                )
            }

            is NetworkException.Unauthorized -> {
                val errorResponse =
                    handleErrorResponse<ErrorWrongConfirmationCodeResponse>(exception.errorBody)
                LoginException.LoginCodeConfirmation.UnauthorizedWrongConfirmationCode(
                    remainingAttempts = errorResponse.remainingAttempts,
                    lockDuration = errorResponse.lockDuration,
                    message = errorResponse.error,
                    statusCode = errorResponse.statusCode
                )
            }

            else -> handleCommonException(exception)
        }
    }

    private fun handleCommonException(exception: NetworkException): LoginException {
        return when (exception) {
            is NetworkException.NoInternetConnection -> {
                val errorResponse = handleErrorResponse<ErrorResponse>(exception.errorBody)
                LoginException.NoConnection(errorResponse.message)
            }

            is NetworkException.Undefined -> {
                LoginException.Undefined()
            }

            else -> {
                LoginException.Undefined()
            }
        }
    }

    private inline fun <reified T> handleErrorResponse(errorMessage: String): T {
        try {
            return json.decodeFromString<T>(errorMessage)

        } catch (e: Exception) {
            throw LoginException.Undefined()
        }
    }

}