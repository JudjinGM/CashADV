package app.cashadvisor.authorization.data.repositoryImpl

import app.cashadvisor.authorization.data.dataSource.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.dataSource.api.TokenLocalDataSource
import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.domain.models.AccountInformation
import app.cashadvisor.authorization.domain.repository.AuthRepository
import app.cashadvisor.common.domain.ExceptionToErrorMapperBase
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.utill.exceptions.NetworkException
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val exceptionToErrorMapperBase: ExceptionToErrorMapperBase
) : AuthRepository {
    override suspend fun loginByEmail(authInputData: AuthInputData): Resource<AccountInformation> {
        return try {
            val data = authRemoteDataSource.loginByEmail(authInputData)
            // какая-то логика по обновлению токена
            tokenLocalDataSource.saveAccessToken(data.accessToken)
            tokenLocalDataSource.saveAccessToken(data.refreshToken)
            // возвращаю, для примера, AccountInformation
            Resource.Success(
                data = AccountInformation.Authorized(
                    token = data.accessToken,
                    refreshToken = data.refreshToken,
                )
            )
        } catch (e: Exception) {
            when (e) {
                is NetworkException.Unauthorized -> {
                    //какие-то действия для специфичных случаев
                    exceptionToErrorMapperBase.mapException(e)
                }

                else -> exceptionToErrorMapperBase.mapException(e)
            }
        }
    }
}