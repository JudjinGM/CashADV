package app.cashadvisor.authorization.domain

import app.cashadvisor.authorization.data.models.ConfirmLoginByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.LoginAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.LoginInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.authorization.data.models.TokenDetailsOutputDto
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginAuthorizationData
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.TokenDetails
import javax.inject.Inject

class LoginDomainMapper @Inject constructor() {

    fun toLoginByEmailInputDto(email: Email, password: Password): LoginInputDto {
        return LoginInputDto(
            email = email.value,
            password = password.value
        )
    }

    fun toLoginData(data: LoginOutputDto): LoginData {
        return LoginData(
            message = data.message
        )
    }

    fun toConfirmLoginByEmailWithCodeInputDto(
        email: Email,
        code: ConfirmCode,
        token: String
    ): ConfirmLoginByEmailWithCodeInputDto {
        return ConfirmLoginByEmailWithCodeInputDto(
            email = email.value,
            code = code.value,
            token = token
        )
    }

    fun toLoginAuthorizationData(data: LoginAuthorizationOutputDto): LoginAuthorizationData {
        return LoginAuthorizationData(
            message = data.message,
            accessTokenLifeTime = data.accessTokenLifeTime,
            refreshTokenLifeTime = data.refreshTokenLifeTime,
            tokenDetails = mapTokenDetails(data.tokenDetailsDto)
        )
    }

    private fun mapTokenDetails(tokenDetailsOutputDto: TokenDetailsOutputDto): TokenDetails {
        return TokenDetails(
            accessToken = tokenDetailsOutputDto.accessToken,
            refreshToken = tokenDetailsOutputDto.refreshToken,
            expiredAt = tokenDetailsOutputDto.expiredAt
        )
    }

}