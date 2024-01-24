package app.cashadvisor.authorization.data.model

class AuthOutputData(
    val accessToken: String,
    val refreshToken: String,
    val statusCode: Int,
    val message: String
)