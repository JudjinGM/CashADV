package app.cashadvisor.authorization.presentation.ui.domain.models

abstract class BaseInputData {
    abstract fun isValid(): Boolean
}