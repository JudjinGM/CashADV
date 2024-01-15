package app.cashadvisor.authorization.data.repositoryImpl

import ErrorsToken
import app.cashadvisor.Resource
import app.cashadvisor.authorization.data.dataSource.TokenLocalDataSource
import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
) : AuthRepository {
    override fun getToken(): Flow<String> = flow {
        emit("token")
    }


    override fun getRefreshToken(): Flow<String> = flow {
        emit("token")
    }
}