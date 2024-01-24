package app.cashadvisor.authorization.data.model

data class ConfirmationWithCodeData(
    val token: String,
    val code: String
)