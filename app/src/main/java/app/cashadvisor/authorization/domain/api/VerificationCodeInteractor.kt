package app.cashadvisor.authorization.presentation.ui.domain.api

import app.cashadvisor.authorization.presentation.ui.domain.models.VerifycationCodeInputData

interface VerificationCodeInteractor {
    fun submitCode(code: VerifycationCodeInputData): Unit // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
    fun requestCode(): Unit // todo тут должен быть какой-то ресурс, который будет возвращать результат валидации
}