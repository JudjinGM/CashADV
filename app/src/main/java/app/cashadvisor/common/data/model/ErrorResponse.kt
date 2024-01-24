package app.cashadvisor.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error") val message: String,
    @SerialName("status_code") val statusCode: Int
)