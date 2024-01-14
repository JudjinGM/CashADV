package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.domain.models.VerifycationCodeInputData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow

interface VerificationCodeInteractor {
    suspend fun submitCode(code: VerifycationCodeInputData): Flow<NetworkResult<Boolean>>
    suspend fun requestCode(): Flow<NetworkResult<Boolean>>
}