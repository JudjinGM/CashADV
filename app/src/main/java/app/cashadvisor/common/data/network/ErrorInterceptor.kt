package app.cashadvisor.common.data.network

import app.cashadvisor.authorization.data.dataSource.api.NetworkConnectionProvider
import app.cashadvisor.common.data.model.ErrorResponse
import app.cashadvisor.common.utill.exceptions.NetworkException
import app.cashadvisor.common.utill.extensions.logNetworkError
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject


class ErrorInterceptor @Inject constructor(
    private val errorCodeMapper: ErrorCodeMapper,
    private val networkConnectionProvider: NetworkConnectionProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkConnectionProvider.isConnected()) {
            throw NetworkException.NoInternetConnection()
        }
        val response = with(chain) { proceed(request()) }
        if (response.code !in (HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_CREATED)) {
            throwErrorIfRequire(response.peekBody(Long.MAX_VALUE), response.code)
        }
        return response
    }

    private fun throwErrorIfRequire(responseBody: ResponseBody, responseCode: Int) {
        var error: IOException? = null
        try {
            val errorResponse =
                Json.decodeFromString<ErrorResponse>(responseBody.string())
            error =
                errorCodeMapper.getException(
                    errorResponse.message,
                    responseCode,
                    errorResponse.statusCode
                )
        } catch (exception: Exception) {
            logNetworkError(exception.message)
        }
        if (error != null) {
            throw error
        }
    }

}