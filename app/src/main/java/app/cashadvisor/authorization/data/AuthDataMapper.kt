package app.cashadvisor.authorization.data

import app.cashadvisor.authorization.data.models.AuthInputDto
import app.cashadvisor.authorization.data.models.AuthorizationOutputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailCodeInputDto
import app.cashadvisor.authorization.data.models.ConfirmEmailOutputDto
import app.cashadvisor.authorization.data.models.ConfirmLoginCodeInputDto
import app.cashadvisor.authorization.data.models.LoginOutputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto
import app.cashadvisor.authorization.data.models.TokenDetailsOutputDto
import app.cashadvisor.authorization.data.models.request.AuthRequest
import app.cashadvisor.authorization.data.models.request.ConfirmCodeRequest
import app.cashadvisor.authorization.data.models.request.ConfirmEmailRequest
import app.cashadvisor.authorization.data.models.response.AuthorizationResponse
import app.cashadvisor.authorization.data.models.response.ConfirmEmailResponse
import app.cashadvisor.authorization.data.models.response.LoginResponse
import app.cashadvisor.authorization.data.models.response.RegisterResponse
import app.cashadvisor.authorization.data.models.response.TokenDetailsDto

class AuthDataMapper {

    fun toAuthRequest(inputDto: AuthInputDto): AuthRequest {
        return AuthRequest(
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

    fun toConfirmCodeRequest(inputDto: ConfirmLoginCodeInputDto): ConfirmCodeRequest {
        return ConfirmCodeRequest(
            email = inputDto.email,
            code = inputDto.code,
            token = inputDto.token
        )
    }

    fun toAuthorizationOutputDto(response: AuthorizationResponse): AuthorizationOutputDto {
        return AuthorizationOutputDto(
            message = response.message,
            statusCode = response.statusCode,
            accessTokenLifeTime = response.accessTokenLifeTime,
            refreshTokenLifeTime = response.refreshTokenLifeTime,
            tokenDetailsDto = toTokenDetailsOutputDto(response.tokenDetailsDto)
        )
    }

    fun toRegisterOutputDto(response: RegisterResponse): RegisterOutputDto {
        return RegisterOutputDto(
            token = response.token,
            statusCode = response.statusCode,
            message = response.message
        )
    }

    fun toConfirmEmailRequest(inputDto: ConfirmEmailCodeInputDto): ConfirmEmailRequest {
        return ConfirmEmailRequest(
            email = inputDto.email,
            code = inputDto.code,
            token = inputDto.token
        )
    }

    fun toConfirmEmailOutputDto(response: ConfirmEmailResponse): ConfirmEmailOutputDto {
        return ConfirmEmailOutputDto(
            statusCode = response.statusCode,
            message = response.message
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