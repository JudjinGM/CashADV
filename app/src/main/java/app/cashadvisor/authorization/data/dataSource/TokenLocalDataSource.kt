package app.cashadvisor.authorization.data.dataSource

import kotlinx.coroutines.flow.Flow

interface TokenLocalDataSource {

    suspend fun saveAccessToken(accessToken: String)
    fun getAccessToken(): Flow<String>
    suspend fun saveRefreshToken(refreshToken: String)
    fun getRefreshToken(): Flow<String>
    suspend fun clearAccessToken()
    suspend fun clearRefreshToken()
}