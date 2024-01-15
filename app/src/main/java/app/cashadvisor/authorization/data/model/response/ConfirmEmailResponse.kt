package app.cashadvisor.authorization.data.model.response

import kotlinx.serialization.SerialName

data class ConfirmEmailResponse(
    val message: String,
    @SerialName("status_code") val statusCode: Int
)