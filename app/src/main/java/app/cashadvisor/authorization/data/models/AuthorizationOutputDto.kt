package app.cashadvisor.authorization.data.models

data class AuthorizationOutputDto(
    val message: String,
    val statusCode: Int,
    val accessTokenLifeTime: Long,
    val refreshTokenLifeTime: Long,
    val tokenDetailsDto: TokenDetailsOutputDto
)