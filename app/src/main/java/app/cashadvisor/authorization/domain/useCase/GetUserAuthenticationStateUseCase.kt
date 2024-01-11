package app.cashadvisor.authorization.domain.useCase

import kotlinx.coroutines.flow.Flow

interface GetUserAuthenticationStateUseCase {
    operator fun invoke(): Flow<Boolean>
}