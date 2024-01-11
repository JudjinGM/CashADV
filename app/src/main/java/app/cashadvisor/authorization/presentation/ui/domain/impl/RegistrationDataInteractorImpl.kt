package app.cashadvisor.authorization.presentation.ui.domain.impl

import app.cashadvisor.authorization.presentation.ui.domain.api.RegistrationDataInteractor
import app.cashadvisor.authorization.presentation.ui.domain.models.LoginData

class RegistrationDataInteractorImpl : RegistrationDataInteractor {
    override fun submitLoginData() {
        // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    }

    override fun isLoginDataValid(data: LoginData): Boolean =
        data.email.isValid() && data.password.isValid()
}