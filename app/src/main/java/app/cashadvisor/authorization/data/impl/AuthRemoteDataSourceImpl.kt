package app.cashadvisor.authorization.data.impl

import app.cashadvisor.authorization.data.AuthDataMapper
import app.cashadvisor.authorization.data.NetworkToAuthExceptionMapper
import app.cashadvisor.authorization.data.api.AuthApiService
import app.cashadvisor.authorization.data.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.models.AuthInputDto
import app.cashadvisor.authorization.data.models.AuthorizationOutputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailCodeInputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailOutputDto
import app.cashadvisor.authorization.data.models.ConfirmLoginCodeInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto
import app.cashadvisor.common.utill.exceptions.NetworkException
import javax.inject.Inject

class AuthRemoteDataSourceImpl
@Inject constructor(
    private val authApiService: AuthApiService,
    private val networkToAuthExceptionMapper: NetworkToAuthExceptionMapper,
    private val authDataMapper: AuthDataMapper
) : AuthRemoteDataSource {

    override suspend fun loginByEmail(inputDto: AuthInputDto): LoginOutputDto {
        return try {
            val response = authApiService.loginByEmail(
                authRequest = authDataMapper.toAuthRequest(inputDto)
            )
            authDataMapper.toLoginOutputDto(response)
        } catch (e: NetworkException) {
            throw networkToAuthExceptionMapper.getExceptionForLogin(e)
        }
    }

    override suspend fun confirmLoginByEmailWithCode(
        inputDto: ConfirmLoginCodeInputDto
    ): AuthorizationOutputDto {
        return try {
            val response = authApiService.confirmLoginWithCode(
                confirmCodeRequest = authDataMapper.toConfirmCodeRequest(inputDto)
            )
            authDataMapper.toAuthorizationOutputDto(response)
        } catch (e: NetworkException) {
            throw networkToAuthExceptionMapper.getExceptionForLoginCodeConfirmation(e)
        }
    }

    override suspend fun registerByEmail(inputDto: AuthInputDto): RegisterOutputDto {
        return try {
            val response = authApiService.register(
                authRequest = authDataMapper.toAuthRequest(inputDto)
            )
            authDataMapper.toRegisterOutputDto(response)
        } catch (e: NetworkException) {
            throw networkToAuthExceptionMapper.getExceptionForRegister(e)
        }
    }

    override suspend fun confirmEmailAndRegistrationWithCode(
        inputDto: ConfirmEmailCodeInputDto
    ): ConfirmEmailOutputDto {
        return try {
            val response = authApiService.confirmEmailWithCode(
                confirmEmailRequest = authDataMapper.toConfirmEmailRequest(inputDto)
            )
            authDataMapper.toConfirmEmailOutputDto(response)
        } catch (e: NetworkException) {
            throw networkToAuthExceptionMapper.getExceptionForEmailCodeConfirmation(e)
        }
    }

}