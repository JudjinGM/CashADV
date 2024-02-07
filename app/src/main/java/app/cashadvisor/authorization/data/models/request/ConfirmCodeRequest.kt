package app.cashadvisor.authorization.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmCodeRequest (
    val email:String,
    val code: String,
    val token: String
)