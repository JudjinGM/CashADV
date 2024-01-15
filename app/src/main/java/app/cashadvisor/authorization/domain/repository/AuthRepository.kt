package app.cashadvisor.authorization.domain.repository

import ErrorsAccessToken
import ErrorsRefreshToken
import app.cashadvisor.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getToken(): Flow<Resource<String, ErrorsAccessToken>>
    fun getRefreshToken():  Flow<Resource<String, ErrorsRefreshToken>>
}