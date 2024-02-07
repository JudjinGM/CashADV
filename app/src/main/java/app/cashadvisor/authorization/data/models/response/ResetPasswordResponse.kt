package app.cashadvisor.authorization.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResetPasswordResponse(
    val message: String,
    @SerialName("status_code") val statusCode: Int
)