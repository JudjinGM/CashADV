package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.data.model.Email
import app.cashadvisor.authorization.data.model.Password
import app.cashadvisor.common.domain.Resource

interface AuthInteractor {
    suspend fun loginByEmail(email: Email, password: Password): Resource<Boolean>
    suspend fun registerByEmail(email: Email, password: Password): Resource<Boolean>
    suspend fun logout():Resource<Boolean>
}