package app.cashadvisor.authorization.domain.models

sealed class AccountInformation {
    object NotAuthorized : AccountInformation()
    data class Authorized(
        // todo пока модель черновая, перепроверить что тут должно быть
        val accessToken: String,
        val refreshToken: String
    ) : AccountInformation()
}