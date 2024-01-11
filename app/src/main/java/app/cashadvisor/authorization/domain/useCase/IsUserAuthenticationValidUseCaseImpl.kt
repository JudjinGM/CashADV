package app.cashadvisor.authorization.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IsUserAuthenticationValidUseCaseImpl : IsUserAuthenticationValidUseCase {

    override fun invoke(): Flow<Boolean> = flow {
        emit(true)
    }

}