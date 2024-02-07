package app.cashadvisor.authorization.domain

import app.cashadvisor.authorization.data.models.AuthInputDto
import app.cashadvisor.authorization.data.models.AuthorizationOutputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailCodeInputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailOutputDto
import app.cashadvisor.authorization.data.models.ConfirmLoginCodeInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto
import app.cashadvisor.authorization.data.models.TokenDetailsOutputDto
import app.cashadvisor.authorization.domain.models.AuthorizationData
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.ConfirmEmailData
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginCodeToken
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.RegisterCodeToken
import app.cashadvisor.authorization.domain.models.RegisterData
import app.cashadvisor.authorization.domain.models.TokenDetails

class AuthDomainMapper {

    fun toAuthInputDto(email: Email, password: Password): AuthInputDto {
        return AuthInputDto(
            email = email.value,
            password = password.value
        )
    }

    fun toLoginData(data: LoginOutputDto): LoginData {
        return LoginData(
            token = LoginCodeToken(data.token),
            message = data.message
        )
    }

    fun toConfirmLoginCodeInputDto(
        email: Email,
        code: ConfirmCode,
        token: LoginCodeToken
    ): ConfirmLoginCodeInputDto {
        return ConfirmLoginCodeInputDto(
            email = email.value,
            code = code.value,
            token = token.value
        )
    }

    fun toAuthorizationData(data: AuthorizationOutputDto): AuthorizationData {
        return AuthorizationData(
            message = data.message,
            accessTokenLifeTime = data.accessTokenLifeTime,
            refreshTokenLifeTime = data.refreshTokenLifeTime,
            tokenDetails = mapTokenDetails(data.tokenDetailsDto)
        )
    }

    fun toRegisterData(data: RegisterOutputDto): RegisterData {
        return RegisterData(
            token = RegisterCodeToken(data.token),
            message = data.message,
        )
    }

    fun toConfirmEmailCodeInputDto(
        email: Email,
        code: ConfirmCode,
        token: RegisterCodeToken
    ): ConfirmEmailCodeInputDto {
        return ConfirmEmailCodeInputDto(
            email = email.value,
            code = code.value,
            token = token.value
        )
    }

    fun toConfirmEmailData(data: ConfirmEmailOutputDto): ConfirmEmailData {
        return ConfirmEmailData(data.message)
    }

    private fun mapTokenDetails(tokenDetailsOutputDto: TokenDetailsOutputDto): TokenDetails {
        return TokenDetails(
            accessToken = tokenDetailsOutputDto.accessToken,
            refreshToken = tokenDetailsOutputDto.refreshToken,
            expiredAt = tokenDetailsOutputDto.expiredAt
        )
    }

}