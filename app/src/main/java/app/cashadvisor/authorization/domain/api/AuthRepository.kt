package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.domain.models.AuthorizationData
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.ConfirmEmailData
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.LoginCodeToken
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.RegisterData
import app.cashadvisor.authorization.domain.models.RegisterCodeToken
import app.cashadvisor.common.domain.Resource

interface AuthRepository {
    suspend fun loginByEmail(
        email: Email,
        password: Password
    ): Resource<LoginData>

    suspend fun registerByEmail(
        email: Email,
        password: Password
    ): Resource<RegisterData>

    suspend fun confirmLoginByEmailWithCode(
        email: Email,
        code: ConfirmCode,
        token: LoginCodeToken
    ): Resource<AuthorizationData>

    suspend fun confirmEmailWithCode(
        email: Email,
        code: ConfirmCode,
        token: RegisterCodeToken
    ): Resource<ConfirmEmailData>

}