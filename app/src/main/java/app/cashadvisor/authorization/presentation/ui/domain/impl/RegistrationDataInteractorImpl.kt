package app.cashadvisor.authorization.presentation.ui.domain.impl

import app.cashadvisor.authorization.presentation.ui.domain.api.RegistrationDataInteractor
import app.cashadvisor.authorization.presentation.ui.domain.models.RegistrationData

class RegistrationDataInteractorImpl : RegistrationDataInteractor {
    override fun submitRegistrationData() {
        // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    }

    override fun isRegistrationDataValid(data: RegistrationData): Boolean =
        data.email.isValid() && data.password.isValid()
}