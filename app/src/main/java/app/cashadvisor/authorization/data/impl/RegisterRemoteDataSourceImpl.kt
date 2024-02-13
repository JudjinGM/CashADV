package app.cashadvisor.authorization.data.impl

import app.cashadvisor.authorization.data.NetworkToRegisterExceptionMapper
import app.cashadvisor.authorization.data.RegisterDataMapper
import app.cashadvisor.authorization.data.api.RegisterApiService
import app.cashadvisor.authorization.data.api.RegisterRemoteDataSource
import app.cashadvisor.authorization.data.models.ConfirmRegisterByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.RegisterAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.RegisterByEmailInputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto
import app.cashadvisor.common.utill.exceptions.NetworkException
import javax.inject.Inject

class RegisterRemoteDataSourceImpl
@Inject constructor(
    private val registerApiService: RegisterApiService,
    private val networkToRegisterExceptionMapper: NetworkToRegisterExceptionMapper,
    private val registerDataMapper: RegisterDataMapper
) : RegisterRemoteDataSource {

    override suspend fun registerByEmail(inputDto: RegisterByEmailInputDto): RegisterOutputDto {
        return try {
            val response = registerApiService.registerByEmail(
                registerByEmailRequest = registerDataMapper.toRegisterByEmailRequest(inputDto)
            )
            registerDataMapper.toRegisterOutputDto(response)
        } catch (exception: NetworkException) {
            throw networkToRegisterExceptionMapper.handleExceptionForRegisterByEmail(exception)
        }
    }

    override suspend fun confirmRegisterByEmailWithCode(
        inputDto: ConfirmRegisterByEmailWithCodeInputDto
    ): RegisterAuthorizationOutputDto {
        return try {
            val response = registerApiService.confirmRegisterByEmailWithCode(
                confirmRegisterByEmailRequest = registerDataMapper.toConfirmRegisterByEmailRequest(
                    inputDto
                )
            )
            registerDataMapper.toRegisterAuthorizationOutputDto(response)
        } catch (exception: NetworkException) {
            throw networkToRegisterExceptionMapper
                .handleExceptionForRegisterConfirmationByEmailWithCode(exception)
        }
    }

}