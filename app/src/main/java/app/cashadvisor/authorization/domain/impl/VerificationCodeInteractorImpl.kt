package app.cashadvisor.authorization.domain.impl

import app.cashadvisor.authorization.domain.api.VerificationCodeInteractor
import app.cashadvisor.authorization.domain.models.VerifycationCodeInputData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class VerificationCodeInteractorImpl: VerificationCodeInteractor {
    override suspend fun submitCode(code: VerifycationCodeInputData): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Success(Random.nextBoolean()))
    }

    override suspend fun requestCode(): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Success(Random.nextBoolean()))
    }
}