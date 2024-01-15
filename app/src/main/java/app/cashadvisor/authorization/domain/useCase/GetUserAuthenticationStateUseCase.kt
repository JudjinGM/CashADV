package app.cashadvisor.authorization.domain.useCase

import ErrorsToken
import app.cashadvisor.Resource
import kotlinx.coroutines.flow.Flow

interface GetUserAuthenticationStateUseCase {
    operator fun invoke(): Flow<Boolean>
}