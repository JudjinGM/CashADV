package app.cashadvisor.common.models

abstract class BaseInputData {

    // todo возможно стоит вместо Boolean возвращать результат валидации с указанием на ошибку
    abstract fun isValid(): Boolean
}