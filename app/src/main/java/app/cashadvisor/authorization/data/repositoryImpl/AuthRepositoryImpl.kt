package app.cashadvisor.authorization.data.repositoryImpl

import app.cashadvisor.authorization.data.dataSource.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.domain.models.AuthData
import app.cashadvisor.authorization.domain.repository.AuthRepository
import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import app.cashadvisor.common.domain.Resource
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val baseExceptionToErrorMapper: BaseExceptionToErrorMapper
) : AuthRepository {
    override suspend fun loginByEmail(authInputData: AuthInputData): Resource<AuthData> {
        return try {
            val data = authRemoteDataSource.loginByEmail(authInputData)
            Resource.Success(
                data = AuthData(
                    accessToken = data.accessToken,
                    refreshToken = data.refreshToken,
                )
            )
        } catch (e: Exception) {
            baseExceptionToErrorMapper.mapException(e)
        }
    }
}