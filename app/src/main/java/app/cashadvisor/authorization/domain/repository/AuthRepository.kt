package app.cashadvisor.authorization.domain.repository

import ErrorsToken
import app.cashadvisor.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getToken(): Flow<String>
    fun getRefreshToken():  Flow<String>
}