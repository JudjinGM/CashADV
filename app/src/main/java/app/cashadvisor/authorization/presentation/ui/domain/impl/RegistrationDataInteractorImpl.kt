package app.cashadvisor.authorization.presentation.ui.domain.impl

import app.cashadvisor.authorization.presentation.ui.domain.api.RegistrationDataInteractor
import app.cashadvisor.authorization.presentation.ui.domain.models.LoginData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class RegistrationDataInteractorImpl : RegistrationDataInteractor {
    override suspend fun submitLoginData(data: LoginData): Flow<NetworkResult<Boolean>> = flow {
        // генерирует случайный результат
        // todo подключить репозиторий для отправки данных на сервер и убрать корутину =flow из сигнатуры
        emit(NetworkResult.Success(Random.nextBoolean()))
    }

    override fun isLoginDataValid(data: LoginData): Boolean =
        data.email.isValid() && data.password.isValid()
}