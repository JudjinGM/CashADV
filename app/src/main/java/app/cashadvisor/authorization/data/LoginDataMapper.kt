package app.cashadvisor.authorization.data

import app.cashadvisor.authorization.data.models.ConfirmLoginByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.LoginAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.LoginInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.authorization.data.models.TokenDetailsOutputDto
import app.cashadvisor.authorization.data.models.request.ConfirmLoginByEmailRequest
import app.cashadvisor.authorization.data.models.request.LoginByEmailRequest
import app.cashadvisor.authorization.data.models.response.ConfirmLoginResponse
import app.cashadvisor.authorization.data.models.response.LoginResponse
import app.cashadvisor.authorization.data.models.response.TokenDetailsDto
import javax.inject.Inject

class LoginDataMapper @Inject constructor() {

    fun toLoginByEmailRequest(inputDto: LoginInputDto): LoginByEmailRequest {
        return LoginByEmailRequest(
            email = inputDto.email,
            password = inputDto.password
        )
    }

    fun toLoginOutputDto(response: LoginResponse): LoginOutputDto {
        return LoginOutputDto(
            token = response.token,
            statusCode = response.statusCode,
            message = response.message
        )
    }

    fun toConfirmLoginByEmailWithCodeRequest(inputDto: ConfirmLoginByEmailWithCodeInputDto): ConfirmLoginByEmailRequest {
        return ConfirmLoginByEmailRequest(
            email = inputDto.email,
            code = inputDto.code,
            token = inputDto.token
        )
    }

    fun toLoginAuthorizationOutputDto(response: ConfirmLoginResponse): LoginAuthorizationOutputDto {
        return LoginAuthorizationOutputDto(
            message = response.message,
            statusCode = response.statusCode,
            accessTokenLifeTime = response.accessTokenLifeTime,
            refreshTokenLifeTime = response.refreshTokenLifeTime,
            tokenDetailsDto = toTokenDetailsOutputDto(response.tokenDetailsDto)
        )
    }

    private fun toTokenDetailsOutputDto(tokenDetailsDto: TokenDetailsDto): TokenDetailsOutputDto {
        return TokenDetailsOutputDto(
            accessToken = tokenDetailsDto.accessToken,
            refreshToken = tokenDetailsDto.refreshToken,
            expiredAt = tokenDetailsDto.expiredAt
        )
    }

}