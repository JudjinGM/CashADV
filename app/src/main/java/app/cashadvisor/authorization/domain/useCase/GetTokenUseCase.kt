package app.cashadvisor.authorization.domain.useCase

import kotlinx.coroutines.flow.Flow

interface GetTokenUseCase {
    suspend fun execute(): Flow<String>
}