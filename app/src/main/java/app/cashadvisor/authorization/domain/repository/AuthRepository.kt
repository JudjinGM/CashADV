package app.cashadvisor.authorization.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getToken(): Flow<String>

}