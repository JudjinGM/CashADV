package app.cashadvisor.authorization.presentation.ui.domain.api

import app.cashadvisor.authorization.presentation.ui.domain.models.LoginData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RegistrationDataInteractor {
    suspend fun submitLoginData(data: LoginData): Flow<NetworkResult<Boolean>>
    fun isLoginDataValid(data: LoginData): Boolean
}