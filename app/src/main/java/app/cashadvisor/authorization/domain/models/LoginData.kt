package app.cashadvisor.authorization.domain.models

data class LoginData(
    val token: LoginCodeToken,
    val message: String,
)