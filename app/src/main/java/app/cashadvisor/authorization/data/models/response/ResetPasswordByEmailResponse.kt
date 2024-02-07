package app.cashadvisor.authorization.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordByEmailResponse(
    val message: String,
    @SerialName("token") val token: String,
    @SerialName("status_code") val statusCode: Int
)