package app.cashadvisor.authorization.data.models

data class ConfirmEmailCodeInputDto(
    val email: String,
    val token: String,
    val code: String
)