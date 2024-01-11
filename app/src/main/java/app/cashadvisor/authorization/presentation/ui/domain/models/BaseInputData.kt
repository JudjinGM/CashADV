package app.cashadvisor.authorization.presentation.ui.domain.models

abstract class BaseInputData {
    // todo возможно стоит возвращать результат валидации вместо булевого значения с указанием на ошибку

    abstract fun isValid(): Boolean
}