package app.cashadvisor.authorization.domain

import app.cashadvisor.authorization.data.models.ConfirmRegisterByEmailWithCodeInputDto
import app.cashadvisor.authorization.data.models.RegisterAuthorizationOutputDto
import app.cashadvisor.authorization.data.models.RegisterByEmailInputDto
import app.cashadvisor.authorization.data.models.RegisterOutputDto
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.RegisterAuthorizationData
import app.cashadvisor.authorization.domain.models.RegisterData
import javax.inject.Inject

class RegisterDomainMapper @Inject constructor() {

    fun toRegisterByEmailInputDto(email: Email, password: Password): RegisterByEmailInputDto {
        return RegisterByEmailInputDto(
            email = email.value,
            password = password.value
        )
    }

    fun toRegisterData(data: RegisterOutputDto): RegisterData {
        return RegisterData(
            message = data.message,
        )
    }

    fun toConfirmRegisterByEmailWithCodeInputDto(
        email: Email,
        code: ConfirmCode,
        token: String
    ): ConfirmRegisterByEmailWithCodeInputDto {
        return ConfirmRegisterByEmailWithCodeInputDto(
            email = email.value,
            code = code.value,
            token = token
        )
    }

    fun toRegisterAuthorizationData(data: RegisterAuthorizationOutputDto): RegisterAuthorizationData {
        return RegisterAuthorizationData(data.message)
    }

}