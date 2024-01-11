package app.cashadvisor.authorization.presentation.ui.domain.impl

import app.cashadvisor.authorization.presentation.ui.domain.api.VerificationCodeInteractor
import app.cashadvisor.authorization.presentation.ui.domain.models.VerifycationCodeInputData

class VerificationCodeInteractorImpl: VerificationCodeInteractor {
    override fun submitCode(code: VerifycationCodeInputData) {
        // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    }

    override fun requestCode() {
        // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    }
}