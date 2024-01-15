package app.cashadvisor.authorization.presentation.ui.domain.models

sealed class AccountInformation {
    object NotAuthorized : AccountInformation()
    data class Authorized(
        // todo пока модель черновая, перепроверить что тут должно быть
        val email: String? = null,
        val token: String? = null,
        val refreshToken: String? = null,
        val isEmailVerified: Boolean = false
    ) : AccountInformation()
}