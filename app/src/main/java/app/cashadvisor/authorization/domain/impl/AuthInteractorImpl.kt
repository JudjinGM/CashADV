package app.cashadvisor.authorization.domain.impl

import app.cashadvisor.authorization.domain.models.Email
import app.cashadvisor.authorization.domain.models.Password
import app.cashadvisor.authorization.domain.api.AuthInteractor
import app.cashadvisor.authorization.domain.api.AuthRepository
import app.cashadvisor.authorization.domain.api.InputValidationInteractor
import app.cashadvisor.authorization.domain.models.ConfirmCode
import app.cashadvisor.authorization.domain.models.LoginCodeToken
import app.cashadvisor.authorization.domain.models.LoginData
import app.cashadvisor.authorization.domain.models.RegisterCodeToken
import app.cashadvisor.authorization.domain.models.RegisterData
import app.cashadvisor.authorization.domain.models.states.ConfirmCodeValidationState
import app.cashadvisor.authorization.domain.models.states.EmailValidationState
import app.cashadvisor.authorization.domain.models.states.PasswordValidationState
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.model.ErrorEntity

class AuthInteractorImpl(
    private val authRepository: AuthRepository,
    private val validationInteractor: InputValidationInteractor
) : AuthInteractor {

    override suspend fun loginByEmail(email: Email, password: Password): Resource<LoginData> {
        val emailValidationState = validationInteractor.validateEmail(email.value)
        val passwordValidationState = validationInteractor.validatePassword(password.value)
        return when (emailValidationState) {

            is EmailValidationState.Success -> {
                when (passwordValidationState) {

                    is PasswordValidationState.Success -> {
                        fetchLoginByEmail(
                            emailValidationState.email,
                            passwordValidationState.password
                        )
                    }

                    is PasswordValidationState.Error -> {
                        Resource.Error(
                            ErrorEntity.Auth.Login.InvalidPasswordInput(
                                passwordValidationState.passwordValidationError
                            )
                        )
                    }
                }
            }

            is EmailValidationState.Error -> {
                return Resource.Error(ErrorEntity.Auth.Login.InvalidEmailInput(emailValidationState.emailValidationError))
            }
        }
    }

    override suspend fun registerByEmail(email: Email, password: Password): Resource<RegisterData> {
        val emailValidationState = validationInteractor.validateEmail(email.value)
        val passwordValidationState = validationInteractor.validatePassword(password.value)
        return when (emailValidationState) {

            is EmailValidationState.Success -> {
                when (passwordValidationState) {

                    is PasswordValidationState.Success -> {
                        fetchRegisterByEmail(
                            emailValidationState.email,
                            passwordValidationState.password
                        )
                    }

                    is PasswordValidationState.Error -> {
                        Resource.Error(
                            ErrorEntity.Auth.Register.InvalidPasswordInput(
                                passwordValidationState.passwordValidationError
                            )
                        )
                    }
                }
            }

            is EmailValidationState.Error -> {
                return Resource.Error(
                    ErrorEntity.Auth.Register.InvalidEmailInput(
                        emailValidationState.emailValidationError
                    )
                )
            }
        }
    }

    override suspend fun confirmLoginByEmailWithCode(
        email: Email,
        code: ConfirmCode,
        token: LoginCodeToken
    ): Resource<String> {
        val codeValidationState = validationInteractor.validateConfirmationCode(code.value)
        val emailValidationState = validationInteractor.validateEmail(email.value)
        return when (codeValidationState) {

            is ConfirmCodeValidationState.Success -> {
                when (emailValidationState) {

                    is EmailValidationState.Success -> {
                        fetchConfirmLogin(email, code, token)
                    }

                    is EmailValidationState.Error -> {
                        Resource.Error(
                            ErrorEntity.Auth.LoginCodeConfirmation.InvalidEmailInput(
                                emailValidationState.emailValidationError
                            )
                        )
                    }
                }
            }

            is ConfirmCodeValidationState.Error -> {
                Resource.Error(
                    ErrorEntity.Auth.LoginCodeConfirmation.InvalidCodeInput(
                        codeValidationState.codeError
                    )
                )
            }
        }

    }

    override suspend fun confirmEmailAndRegistrationWithCode(
        email: Email,
        code: ConfirmCode,
        token: RegisterCodeToken
    ): Resource<String> {
        val codeValidationState = validationInteractor.validateConfirmationCode(code.value)
        val emailValidationState = validationInteractor.validateEmail(email.value)
        return when (codeValidationState) {

            is ConfirmCodeValidationState.Success -> {
                when (emailValidationState) {

                    is EmailValidationState.Success -> {
                        fetchConfirmEmail(email, code, token)
                    }

                    is EmailValidationState.Error -> {
                        Resource.Error(
                            ErrorEntity.Auth.EmailCodeConfirmation.InvalidEmailInput(
                                emailValidationState.emailValidationError
                            )
                        )
                    }
                }
            }

            is ConfirmCodeValidationState.Error -> {
                Resource.Error(
                    ErrorEntity.Auth.EmailCodeConfirmation.InvalidCodeInput(
                        codeValidationState.codeError
                    )
                )
            }
        }
    }

    private suspend fun fetchLoginByEmail(
        email: Email,
        password: Password
    ): Resource<LoginData> {
        val result = authRepository.loginByEmail(email, password)
        return when (result) {
            is Resource.Success -> {
                Resource.Success(data = result.data)
            }

            is Resource.Error -> {
                Resource.Error(error = result.error)
            }
        }
    }

    private suspend fun fetchRegisterByEmail(
        email: Email,
        password: Password
    ): Resource<RegisterData> {
        val result =
            authRepository.registerByEmail(email, password)
        return when (result) {
            is Resource.Success -> {
                Resource.Success(data = result.data)
            }

            is Resource.Error -> {
                Resource.Error(error = result.error)
            }
        }
    }

    private suspend fun fetchConfirmLogin(
        email: Email,
        code: ConfirmCode,
        token: LoginCodeToken
    ): Resource<String> {
        val result = authRepository.confirmLoginByEmailWithCode(email, code, token)
        return when (result) {
            is Resource.Success -> {
                //TODO: вызов метода создания нового аккаунта репозитория аккаунт
                Resource.Success(data = result.data.message)
            }

            is Resource.Error -> {
                Resource.Error(error =  result.error)
            }
        }
    }

    private suspend fun fetchConfirmEmail(
        email: Email,
        code: ConfirmCode,
        token: RegisterCodeToken
    ): Resource<String> {
        val result = authRepository.confirmEmailWithCode(email, code, token)
        return when (result) {
            is Resource.Success -> {
                //TODO: вызов метода создания нового аккаунта репозитория аккаунт
                Resource.Success(result.data.message)
            }

            is Resource.Error -> {
                Resource.Error(result.error)
            }
        }
    }

}