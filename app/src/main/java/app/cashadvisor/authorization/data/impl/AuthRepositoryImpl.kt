package app.cashadvisor.authorization.data.impl

import app.cashadvisor.authorization.data.api.AuthRemoteDataSource
import app.cashadvisor.authorization.domain.AuthDomainMapper
import app.cashadvisor.authorization.domain.api.AuthRepository
import app.cashadvisor.authorization.domain.models.AuthorizationData
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.ConfirmEmailData
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginCodeToken
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.RegisterCodeToken
import app.cashadvisor.authorization.domain.models.RegisterData
import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import app.cashadvisor.common.domain.Resource
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val exceptionToErrorMapper: BaseExceptionToErrorMapper,
    private val authDomainMapper: AuthDomainMapper
) : AuthRepository {
    override suspend fun loginByEmail(email: Email, password: Password): Resource<LoginData> {
        return try {
            val data = authRemoteDataSource.loginByEmail(
                inputDto = authDomainMapper.toAuthInputDto(email, password)
            )
            Resource.Success(
                data = authDomainMapper.toLoginData(data)
            )
        } catch (e: Exception) {
            exceptionToErrorMapper.mapException(e)
        }
    }

    override suspend fun confirmLoginByEmailWithCode(
        email: Email,
        code: ConfirmCode,
        token: LoginCodeToken
    ): Resource<AuthorizationData> {
        return try {
            val data = authRemoteDataSource.confirmLoginByEmailWithCode(
                inputDto = authDomainMapper.toConfirmLoginCodeInputDto(email, code, token)
            )
            Resource.Success(
                data = authDomainMapper.toAuthorizationData(data)
            )
        } catch (e: Exception) {
            exceptionToErrorMapper.mapException(e)
        }
    }

    override suspend fun registerByEmail(email: Email, password: Password): Resource<RegisterData> {
        return try {
            val data = authRemoteDataSource.registerByEmail(
                inputDto = authDomainMapper.toAuthInputDto(email, password)
            )
            Resource.Success(
                data = authDomainMapper.toRegisterData(data)
            )
        } catch (e: Exception) {
            exceptionToErrorMapper.mapException(e)
        }
    }

    override suspend fun confirmEmailWithCode(
        email: Email,
        code: ConfirmCode,
        token: RegisterCodeToken
    ): Resource<ConfirmEmailData> {
        return try {
            val data = authRemoteDataSource.confirmEmailAndRegistrationWithCode(
                inputDto = authDomainMapper.toConfirmEmailCodeInputDto(email, code, token)
            )
            Resource.Success(
                data = authDomainMapper.toConfirmEmailData(data)
            )
        } catch (e: Exception) {
            exceptionToErrorMapper.mapException(e)
        }
    }

}