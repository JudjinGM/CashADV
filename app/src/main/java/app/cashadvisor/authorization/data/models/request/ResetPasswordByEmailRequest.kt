package app.cashadvisor.authorization.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordByEmailRequest(
    val email: String
)