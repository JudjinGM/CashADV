package app.cashadvisor.common.data.network

import app.cashadvisor.authorization.data.api.NetworkConnectionProvider
import app.cashadvisor.common.utill.exceptions.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject


class ErrorInterceptor @Inject constructor(
    private val networkErrorCodeToExceptionMapper: NetworkErrorCodeToExceptionMapper,
    private val networkConnectionProvider: NetworkConnectionProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!networkConnectionProvider.isConnected()) {
            throw NetworkException.NoInternetConnection()
        }
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code !in (HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_CREATED)) {
            throwErrorIfRequire(response.peekBody(Long.MAX_VALUE).string(), response.code)
        }
        return response
    }

    private fun throwErrorIfRequire(
        errorMessage: String, responseCode: Int,
    ) {
        throw networkErrorCodeToExceptionMapper.getException(
            errorMessage = errorMessage, responseCode = responseCode
        )
    }
}