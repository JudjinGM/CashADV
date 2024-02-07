package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginCodeToken
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.RegisterCodeToken
import app.cashadvisor.authorization.domain.models.RegisterData
import app.cashadvisor.common.domain.Resource

interface AuthInteractor {
    suspend fun loginByEmail(email: Email, password: Password): Resource<LoginData>
    suspend fun registerByEmail(email: Email, password: Password): Resource<RegisterData>
    suspend fun confirmLoginByEmailWithCode(
        email: Email,
        code: ConfirmCode,
        token: LoginCodeToken
    ): Resource<String>

    suspend fun confirmEmailAndRegistrationWithCode(
        email: Email,
        code: ConfirmCode,
        token: RegisterCodeToken
    ): Resource<String>
}