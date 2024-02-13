package app.cashadvisor.authorization.data

import app.cashadvisor.authorization.data.models.ConfirmRegisterByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.RegisterAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.RegisterByEmailInputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto
import app.cashadvisor.authorization.data.models.request.ConfirmRegisterByEmailRequest
import app.cashadvisor.authorization.data.models.request.RegisterByEmailRequest
import app.cashadvisor.authorization.data.models.response.ConfirmRegisterResponse
import app.cashadvisor.authorization.data.models.response.RegisterResponse
import javax.inject.Inject

class RegisterDataMapper @Inject constructor() {

    fun toRegisterByEmailRequest(inputDto: RegisterByEmailInputDto): RegisterByEmailRequest {
        return RegisterByEmailRequest(
            email = inputDto.email,
            password = inputDto.password
        )
    }

    fun toRegisterOutputDto(response: RegisterResponse): RegisterOutputDto {
        return RegisterOutputDto(
            token = response.token,
            statusCode = response.statusCode,
            message = response.message
        )
    }

    fun toConfirmRegisterByEmailRequest(inputDto: ConfirmRegisterByEmailWithCodeInputDto): ConfirmRegisterByEmailRequest {
        return ConfirmRegisterByEmailRequest(
            email = inputDto.email,
            code = inputDto.code,
            token = inputDto.token
        )
    }

    fun toRegisterAuthorizationOutputDto(response: ConfirmRegisterResponse): RegisterAuthorizationOutputDto {
        return RegisterAuthorizationOutputDto(
            statusCode = response.statusCode,
            message = response.message
        )
    }

}