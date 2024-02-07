package app.cashadvisor.authorization.data.models

data class ConfirmLoginCodeInputDto (
    val email:String,
    val code: String,
    val token: String,
)