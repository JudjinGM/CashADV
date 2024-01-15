package app.cashadvisor.authorization.domain.useCase

import ErrorsRefreshToken
import app.cashadvisor.Resource
import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetRefreshTokenStateUseCase {
    operator fun invoke(): Flow<Resource<Boolean, ErrorsRefreshToken>>

    class Base(private val authRepository: AuthRepository) : GetRefreshTokenStateUseCase {
        override fun invoke(): Flow<Resource<Boolean, ErrorsRefreshToken>> =
            authRepository.getRefreshToken().map {
                when (it) {
                    is Resource.Error -> Resource.Error(error = it.error ?: ErrorsRefreshToken.ErrorRefreshTokenThree)

                    is Resource.Success -> Resource.Success(data = it.data?.isNotEmpty() ?: false)

                }
            }
    }
}

