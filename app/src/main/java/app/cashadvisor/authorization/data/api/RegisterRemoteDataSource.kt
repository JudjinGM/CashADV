package app.cashadvisor.authorization.data.api

import app.cashadvisor.authorization.data.models.ConfirmRegisterByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.RegisterAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.RegisterByEmailInputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto

interface RegisterRemoteDataSource {
    suspend fun registerByEmail(inputDto: RegisterByEmailInputDto): RegisterOutputDto
    suspend fun confirmRegisterByEmailWithCode(inputDto: ConfirmRegisterByEmailWithCodeInputDto): RegisterAuthorizationOutputDto
}