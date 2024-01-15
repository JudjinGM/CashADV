package app.cashadvisor.authorization.domain.useCase

import ErrorsToken
import app.cashadvisor.Resource
import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetRefreshTokenStateUseCase {
    operator fun invoke(): Flow<Resource<Boolean, ErrorsToken>>

    class Base(private val authRepository: AuthRepository) : GetRefreshTokenStateUseCase {
        override fun invoke(): Flow<Resource<Boolean, ErrorsToken>> =
            authRepository.getRefreshToken().map {
                when (it) {
                    is Resource.Error -> Resource.Error(error = it.error ?: ErrorsToken.ErrorThree)

                    is Resource.Success -> Resource.Success(data = it.data?.isNotEmpty() ?: false)

                }
            }
    }
}

