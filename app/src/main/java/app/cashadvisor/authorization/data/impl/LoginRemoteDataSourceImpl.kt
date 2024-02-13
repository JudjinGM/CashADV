package app.cashadvisor.authorization.data.impl

import app.cashadvisor.authorization.data.LoginDataMapper
import app.cashadvisor.authorization.data.NetworkToLoginExceptionMapper
import app.cashadvisor.authorization.data.api.LoginApiService
import app.cashadvisor.authorization.data.api.LoginRemoteDataSource
import app.cashadvisor.authorization.data.models.ConfirmLoginByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.LoginAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.LoginInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.common.utill.exceptions.NetworkException
import javax.inject.Inject

class LoginRemoteDataSourceImpl
@Inject constructor(
    private val loginApiService: LoginApiService,
    private val networkToLoginExceptionMapper: NetworkToLoginExceptionMapper,
    private val loginDataMapper: LoginDataMapper
) : LoginRemoteDataSource {

    override suspend fun loginByEmail(inputDto: LoginInputDto): LoginOutputDto {
        return try {
            val response = loginApiService.loginByEmail(
                loginByEmailRequest = loginDataMapper.toLoginByEmailRequest(inputDto)
            )
            loginDataMapper.toLoginOutputDto(response)
        } catch (exception: NetworkException) {
            throw networkToLoginExceptionMapper.handleExceptionForLoginByEmail(exception)
        }
    }

    override suspend fun confirmLoginByEmailWithCode(
        inputDto: ConfirmLoginByEmailWithCodeInputDto
    ): LoginAuthorizationOutputDto {
        return try {
            val response = loginApiService.confirmLoginByEmailWithCode(
                confirmLoginByEmailRequest = loginDataMapper.toConfirmLoginByEmailWithCodeRequest(
                    inputDto
                )
            )
            loginDataMapper.toLoginAuthorizationOutputDto(response)
        } catch (exception: NetworkException) {
            throw networkToLoginExceptionMapper
                .handleExceptionForLoginConfirmationByEmailWithCode(exception)
        }
    }

}