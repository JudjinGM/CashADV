package app.cashadvisor.authorization.presentation.ui.domain.models

data class RegistrationData (
    val email: EMailInputData,
    val password: PasswordInputData,
)