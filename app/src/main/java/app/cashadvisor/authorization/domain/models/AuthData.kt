package app.cashadvisor.authorization.domain.models

data class AuthData(
    val accessToken: String,
    val refreshToken: String
)