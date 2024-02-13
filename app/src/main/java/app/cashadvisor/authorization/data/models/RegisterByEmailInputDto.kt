package app.cashadvisor.authorization.data.models

data class RegisterByEmailInputDto(
    val email: String,
    val password: String
)