package app.cashadvisor.authorization.data.impl

import app.cashadvisor.authorization.data.api.LoginRemoteDataSource
import app.cashadvisor.authorization.di.LoginExceptionMapper
import app.cashadvisor.authorization.domain.LoginDomainMapper
import app.cashadvisor.authorization.domain.api.LoginRepository
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginAuthorizationData
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.states.LoginState
import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.model.ErrorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginRepositoryImpl
@Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    @LoginExceptionMapper private val exceptionToErrorMapper: BaseExceptionToErrorMapper,
    private val loginDomainMapper: LoginDomainMapper
) : LoginRepository {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    private val state = _state.asStateFlow()
    private val currentState: LoginState
        get() = state.replayCache.firstOrNull() ?: LoginState(LoginState.State.Initial)

    override suspend fun loginByEmail(email: Email, password: Password): Resource<LoginData> {
        return try {
            val data = loginRemoteDataSource.loginByEmail(
                inputDto = loginDomainMapper.toLoginByEmailInputDto(email, password)
            )

            _state.update {
                it.copy(state = LoginState.State.InProcess(codeToken = data.token))
            }
            Resource.Success(
                data = loginDomainMapper.toLoginData(data)
            )
        } catch (exception: Exception) {
            _state.update {
                it.copy(state = LoginState.State.Initial)
            }
            Resource.Error(
                exceptionToErrorMapper.handleException(exception)
            )
        }
    }

    override suspend fun confirmLoginByEmailWithCode(
        email: Email,
        code: ConfirmCode,
    ): Resource<LoginAuthorizationData> {
        return try {
            val token: String

            when (val state = currentState.state) {
                is LoginState.State.InProcess -> {
                    token = state.codeToken
                }

                else -> {
                    return Resource.Error(
                        ErrorEntity.RegisterByEmailConfirmationWithCode.InvalidToken(
                            WRONG_STATE_ERROR
                        )
                    )
                }
            }
            _state.update {
                it.copy(state = LoginState.State.Initial)
            }
            val data = loginRemoteDataSource.confirmLoginByEmailWithCode(
                inputDto = loginDomainMapper.toConfirmLoginByEmailWithCodeInputDto(
                    email,
                    code,
                    token
                )
            )
            Resource.Success(
                data = loginDomainMapper.toLoginAuthorizationData(data)
            )
        } catch (exception: Exception) {
            Resource.Error(
                exceptionToErrorMapper.handleException(exception)
            )
        }
    }

    override fun isLoginInProgress(): Flow<Boolean> {
        return state.map { it.state is LoginState.State.InProcess }
    }

    companion object {
        const val WRONG_STATE_ERROR = "Login is not in progress"
    }

}