package app.cashadvisor.authorization.data.model.response

import kotlinx.serialization.SerialName

data class LoginResponse(
    val message: String,
    @SerialName("status_code") val statusCode: Int = 0,
    @SerialName("token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String
)