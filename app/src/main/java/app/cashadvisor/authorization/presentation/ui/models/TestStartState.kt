package app.cashadvisor.authorization.presentation.ui.models

import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.LoginCodeToken
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.RegisterCodeToken

data class TestStartState(
    val email: Email = Email("text6@example.com"),
    val password: Password = Password("password"),
    val emailCode: ConfirmCode = ConfirmCode(""),
    val loginCode: ConfirmCode = ConfirmCode(""),
    val registerToken: RegisterCodeToken = RegisterCodeToken(""),
    val loginToken: LoginCodeToken = LoginCodeToken(""),
    val isEmailCodeValid: Boolean = false,
    val isLoginCodeValid: Boolean = false,
    val messageForUser: String = ""
)