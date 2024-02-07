package app.cashadvisor.authorization.domain.models

data class RegisterData(
    val token: RegisterCodeToken,
    val message: String,
)