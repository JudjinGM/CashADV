package app.cashadvisor.authorization.data.model.response

import kotlinx.serialization.SerialName

class ResetPasswordResponse(
    val message: String,
    @SerialName("status_code") val statusCode: Int
)