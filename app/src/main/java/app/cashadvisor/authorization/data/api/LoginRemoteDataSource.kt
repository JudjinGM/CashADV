package app.cashadvisor.authorization.data.api

import app.cashadvisor.authorization.data.models.ConfirmLoginByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.LoginAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.LoginInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto

interface LoginRemoteDataSource {
    suspend fun loginByEmail(inputDto: LoginInputDto): LoginOutputDto
    suspend fun confirmLoginByEmailWithCode(inputDto: ConfirmLoginByEmailWithCodeInputDto): LoginAuthorizationOutputDto
}