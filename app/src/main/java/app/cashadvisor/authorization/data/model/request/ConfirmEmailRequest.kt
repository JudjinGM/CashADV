package app.cashadvisor.authorization.data.model.request

data class ConfirmEmailRequest(
    val code: String,
    val token: String
)