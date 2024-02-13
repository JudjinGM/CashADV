package app.cashadvisor.authorization.domain.impl

import app.cashadvisor.authorization.domain.api.InputValidationInteractor
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.ConfirmCodeValidationError
import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.EmailValidationError
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.models.PasswordValidationError
import app.cashadvisor.authorization.domain.models.states.ConfirmCodeValidationState
import app.cashadvisor.authorization.domain.models.states.EmailValidationState
import app.cashadvisor.authorization.domain.models.states.PasswordValidationState
import javax.inject.Inject

class InputValidationInteractorImpl @Inject constructor() : InputValidationInteractor {
    override suspend fun validateEmail(email: String): EmailValidationState {
        val isEmailValid = isValidText(email, REGEX_PATTERN_EMAIL)
        return if (isEmailValid) {
            EmailValidationState.Success(Email(value = email))
        } else EmailValidationState.Error(
            email = Email(EMPTY_VALUE),
            emailValidationError = EmailValidationError.EMAIL_NOT_VALID
        )
    }

    override suspend fun validatePassword(password: String): PasswordValidationState {
        val isPasswordValid = isValidText(password, REGEX_PATTERN_PASSWORD)
        return if (isPasswordValid) {
            PasswordValidationState.Success(Password(value = password))
        } else PasswordValidationState.Error(
            password = Password(EMPTY_VALUE),
            passwordValidationError = PasswordValidationError.PASSWORD_NOT_VALID
        )
    }

    override suspend fun validateConfirmationCode(code: String): ConfirmCodeValidationState {
        return if (isValidCode(code, REGEX_PATTERN_CODE)) {
            ConfirmCodeValidationState.Success(confirmCode = ConfirmCode(code))
        } else ConfirmCodeValidationState.Error(
            confirmCode = ConfirmCode(EMPTY_VALUE),
            codeError = ConfirmCodeValidationError.CODE_NOT_VALID
        )
    }

    private fun isValidText(
        text: String,
        regexPattern: String,
    ): Boolean = text.matches(Regex(regexPattern))

    private fun isValidCode(text: String, regexPattern: String): Boolean {
        return text.matches(Regex(regexPattern))
    }

    companion object {
        const val REGEX_PATTERN_EMAIL =
            """^(?=(?:(?!.*[_.-]{2,})(?!.*[_.-]$)(?!^[-_.])[a-zA-Z0-9._-]{1,49}[a-zA-Z0-9]@(?!-)[a-zA-Z0-9-]{1,63}(\.[a-zA-Z]{2,})+)$)(?=(?:.{7,114})$).*"""
        const val REGEX_PATTERN_PASSWORD = """^[a-zA-Z0-9_]{8,30}$"""
        const val REGEX_PATTERN_CODE = """^[0-9]{4}$"""
        const val EMPTY_VALUE = ""
    }
}