package app.cashadvisor.authorization.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthByServiceRequest(
    val serviceToken: String
)