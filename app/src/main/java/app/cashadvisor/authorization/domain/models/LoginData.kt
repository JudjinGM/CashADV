package app.cashadvisor.authorization.domain.models

data class LoginData (

    val email: EMailInputData,
    val password: PasswordInputData,
)