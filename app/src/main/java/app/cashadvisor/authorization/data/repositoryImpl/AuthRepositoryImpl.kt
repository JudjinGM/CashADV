package app.cashadvisor.authorization.data.repositoryImpl

import ErrorsAccessToken
import ErrorsRefreshToken
import app.cashadvisor.Resource
import app.cashadvisor.authorization.data.dataSource.TokenLocalDataSource
import app.cashadvisor.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
) : AuthRepository {
    override fun getToken(): Flow<Resource<String, ErrorsAccessToken>> = flow {
        emit(Resource.Success<String, ErrorsAccessToken>("token"))
    }


    override fun getRefreshToken(): Flow<Resource<String, ErrorsRefreshToken>> = flow {
        emit(Resource.Success<String, ErrorsRefreshToken>("token"))
    }
}