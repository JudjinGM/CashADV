package app.cashadvisor.authorization.domain.useCase

import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserAuthenticationStateUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetUserAuthenticationStateUseCase {

    override fun invoke(): Flow<Boolean> {
       return authRepository.getToken().map { token ->
            token.isNotBlank()
        }
    }
}