package app.cashadvisor.authorization.presentation.ui.domain.api

import app.cashadvisor.authorization.presentation.ui.domain.models.LoginData

interface RegistrationDataInteractor {
    fun submitLoginData(): Unit // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    fun isLoginDataValid(data: LoginData): Boolean
}