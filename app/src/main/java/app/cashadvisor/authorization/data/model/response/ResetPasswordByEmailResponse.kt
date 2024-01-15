package app.cashadvisor.authorization.data.model.response

import kotlinx.serialization.SerialName

data class ResetPasswordByEmailResponse(
    val message: String,
    @SerialName("token") val token: String,
    @SerialName("status_code") val statusCode: Int
)