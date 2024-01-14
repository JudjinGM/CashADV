package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.PasswordInputData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RegistrationDataInteractor {
    suspend fun submitLoginData(data: LoginData): Flow<NetworkResult<Boolean>>
    fun isLoginDataValid(data: LoginData): Boolean
    suspend fun requestPasswordReset(): Flow<NetworkResult<Boolean>>
    suspend fun setNewPassword(data: PasswordInputData): Flow<NetworkResult<Boolean>>
}