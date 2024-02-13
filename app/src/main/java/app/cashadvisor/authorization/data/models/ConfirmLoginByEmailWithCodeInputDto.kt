package app.cashadvisor.authorization.data.models

data class ConfirmLoginByEmailWithCodeInputDto(
    val email:String,
    val code: String,
    val token: String,
)