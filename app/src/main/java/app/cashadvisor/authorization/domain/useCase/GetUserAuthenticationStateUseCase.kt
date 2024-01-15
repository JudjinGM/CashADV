package app.cashadvisor.authorization.domain.useCase

import ErrorsAccessToken
import app.cashadvisor.Resource
import kotlinx.coroutines.flow.Flow

interface GetUserAuthenticationStateUseCase {
    operator fun invoke(): Flow<Resource<Boolean, ErrorsAccessToken>>
}