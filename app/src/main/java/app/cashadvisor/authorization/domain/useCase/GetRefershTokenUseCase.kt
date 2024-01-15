package app.cashadvisor.authorization.domain.useCase

import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetRefreshTokenStateUseCase {
    operator fun invoke(): Flow<Boolean>

    class Base(private val authRepository: AuthRepository) : GetRefreshTokenStateUseCase {
        override fun invoke(): Flow<Boolean> =
            authRepository.getRefreshToken().map {
                it.isNotBlank()
            }
    }
}

