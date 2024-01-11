package app.cashadvisor.authorization.domain.useCase

import kotlinx.coroutines.flow.Flow

interface IsUserAuthenticationValidUseCase {
    operator fun invoke(): Flow<Boolean>
}