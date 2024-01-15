package app.cashadvisor.authorization.data.model.request

data class AuthRequest(
    val email: String,
    val password: String
)