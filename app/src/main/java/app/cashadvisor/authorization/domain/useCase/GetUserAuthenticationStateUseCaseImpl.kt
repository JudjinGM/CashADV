package app.cashadvisor.authorization.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserAuthenticationStateUseCaseImpl : GetUserAuthenticationStateUseCase {

    override fun invoke(): Flow<Boolean> = flow {
        emit(true)
    }

}