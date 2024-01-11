package app.cashadvisor.authorization.presentation.ui.domain.api

import app.cashadvisor.authorization.presentation.ui.domain.models.RegistrationData

interface RegistrationDataInteractor {
    fun submitRegistrationData(): Unit // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    fun isRegistrationDataValid(data: RegistrationData): Boolean
}