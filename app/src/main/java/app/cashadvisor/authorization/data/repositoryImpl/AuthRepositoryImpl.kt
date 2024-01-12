package app.cashadvisor.authorization.data.repositoryImpl

import app.cashadvisor.authorization.data.dataSource.TokenLocalDataSource
import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
) : AuthRepository {
    override fun getToken(): Flow<String> {
        return tokenLocalDataSource.getAccessToken()
    }
}