package app.cashadvisor.authorization.data.api

import app.cashadvisor.authorization.data.models.AuthInputDto
import app.cashadvisor.authorization.data.models.AuthorizationOutputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailCodeInputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailOutputDto
import app.cashadvisor.authorization.data.models.ConfirmLoginCodeInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto

interface AuthRemoteDataSource {
    suspend fun loginByEmail(inputDto: AuthInputDto): LoginOutputDto
    suspend fun confirmLoginByEmailWithCode(inputDto: ConfirmLoginCodeInputDto): AuthorizationOutputDto
    suspend fun registerByEmail(inputDto: AuthInputDto): RegisterOutputDto
    suspend fun confirmEmailAndRegistrationWithCode(inputDto: ConfirmEmailCodeInputDto): ConfirmEmailOutputDto

}