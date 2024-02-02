package app.cashadvisor.authorization.domain.repository

import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.domain.models.AuthData
import app.cashadvisor.common.domain.Resource

interface AuthRepository {
    suspend fun loginByEmail(authInputData: AuthInputData): Resource<AuthData>
}