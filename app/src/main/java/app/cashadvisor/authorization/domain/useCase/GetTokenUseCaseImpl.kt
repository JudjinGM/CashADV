package app.cashadvisor.authorization.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTokenUseCaseImpl : GetTokenUseCase {
    override suspend fun execute(): Flow<String> = flow {
        emit(MOCK_TOKEN)
    }

    companion object {
        const val MOCK_EMPTY_TOKEN = ""
        const val MOCK_TOKEN = "token"
    }
}