package app.cashadvisor.authorization.data.dataSource.api

interface TokenLocalDataSource {

    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
}