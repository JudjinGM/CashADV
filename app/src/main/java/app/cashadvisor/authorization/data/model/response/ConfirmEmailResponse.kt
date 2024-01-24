package app.cashadvisor.authorization.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmEmailResponse(
    val message: String,
    @SerialName("status_code") val statusCode: Int
)