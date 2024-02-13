package app.cashadvisor.authorization.data.models

data class ConfirmRegisterByEmailWithCodeInputDto(
    val email: String,
    val token: String,
    val code: String
)