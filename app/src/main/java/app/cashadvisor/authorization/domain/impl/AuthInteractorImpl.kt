package app.cashadvisor.authorization.domain.impl

import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.data.model.Email
import app.cashadvisor.authorization.data.model.Password
import app.cashadvisor.authorization.domain.api.AuthInteractor
import app.cashadvisor.authorization.domain.repository.AuthRepository
import app.cashadvisor.common.domain.Resource

class AuthInteractorImpl(
    private val authRepository: AuthRepository,
    //private val accountRepository: AuthRepository
) : AuthInteractor {

    //пока здесь Boolean, все зависит, что будет возвращаться из accountRepository
    override suspend fun loginByEmail(email: Email, password: Password): Resource<Boolean> {
        val authData =
            authRepository.loginByEmail(AuthInputData(email = email, password = password))
        return when (authData) {
            is Resource.Success -> {
                //accountRepository.createAccount(authData)
                Resource.Success(true)
            }

            is Resource.Error -> {
                Resource.Error(error = authData.error)
            }
        }
    }

    override suspend fun registerByEmail(email: Email, password: Password): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}