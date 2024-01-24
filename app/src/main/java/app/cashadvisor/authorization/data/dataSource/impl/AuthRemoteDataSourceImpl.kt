package app.cashadvisor.authorization.data.dataSource.impl

import app.cashadvisor.authorization.data.dataSource.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.data.model.AuthOutputData
import app.cashadvisor.authorization.data.model.request.AuthRequest
import app.cashadvisor.authorization.data.network.AuthApiService
import javax.inject.Inject

class AuthRemoteDataSourceImpl
@Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRemoteDataSource {

    override suspend fun loginByEmail(authInputData: AuthInputData): AuthOutputData {
        val request = AuthRequest(
            email = authInputData.email.value,
            password = authInputData.password.value
        )
        val response = authApiService.loginByEmail(request)
        return AuthOutputData(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            statusCode = response.statusCode,
            message = response.message
        )
    }

}