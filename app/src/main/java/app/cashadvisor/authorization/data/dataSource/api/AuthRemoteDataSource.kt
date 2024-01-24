package app.cashadvisor.authorization.data.dataSource.api

import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.data.model.AuthOutputData

interface AuthRemoteDataSource {
    suspend fun loginByEmail(authInputData: AuthInputData): AuthOutputData
}