package app.cashadvisor.authorization.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.domain.api.AuthInteractor
import app.cashadvisor.authorization.domain.api.InputValidationInteractor
import app.cashadvisor.authorization.domain.models.states.ConfirmCodeValidationState
import app.cashadvisor.authorization.presentation.ui.models.TestStartState
import app.cashadvisor.authorization.presentation.ui.models.TestStartUiState
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.extensions.logDebugMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val inputValidationInteractor: InputValidationInteractor
) : ViewModel() {

    //TODO: отладочная вьюмодель, перед мерджем будет очищена вместе со стейт моделями

    private val _uiState: MutableStateFlow<TestStartUiState> =
        MutableStateFlow(TestStartUiState())

    val uiState: StateFlow<TestStartUiState> = _uiState.asStateFlow()

    private val _state: MutableStateFlow<TestStartState> =
        MutableStateFlow(TestStartState())

    private val state: StateFlow<TestStartState> = _state.asStateFlow()

    private val currentState get() = state.replayCache.firstOrNull() ?: TestStartState()

    fun init() {
        viewModelScope.launch {
            state.collect { state ->
                _uiState.update { uiState ->
                    uiState.copy(
                        emailCodeIsValid = state.isEmailCodeValid,
                        loginCodeIsValid = state.isLoginCodeValid,
                        message = state.messageForUser
                    )
                }

            }
        }
    }

    fun register() {
        viewModelScope.launch {
            val result = authInteractor.registerByEmail(
                currentState.email,
                currentState.password
            )

            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Message register ${result.data.message}")
                    logDebugMessage("Token register ${result.data.token}")
                    _state.update {
                        it.copy(
                            registerToken = result.data.token,
                            messageForUser = result.data.message
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(messageForUser = "Error: ${result.error.message}")
                    }

                    when (result.error) {

                        is ErrorEntity.NetworksError.NoInternet -> {
                            logDebugMessage("NoInternet ${result.error.message}")
                        }

                        is ErrorEntity.Auth.Register -> {
                            when (result.error) {
                                is ErrorEntity.Auth.Register.FailedToGenerateTokenOrSendEmail -> {
                                    logDebugMessage("FailedToGenerateTokenOrSendEmail ${result.error.message}")
                                }

                                is ErrorEntity.Auth.Register.InvalidEmail -> {
                                    logDebugMessage("InvalidEmail ${result.error.message}")

                                }

                                is ErrorEntity.Auth.Register.InvalidEmailInput -> {
                                    logDebugMessage("InvalidEmailInput ${result.error.message}")
                                }

                                is ErrorEntity.Auth.Register.InvalidPasswordInput -> {
                                    logDebugMessage("InvalidPasswordInput ${result.error.message}")
                                }
                            }
                        }

                        else -> {
                            logDebugMessage("Something went wrong ${result.error.message}")
                        }
                    }
                }

            }
        }
    }

    fun sendEmailConfirmCode() {
        viewModelScope.launch {
            logDebugMessage("email: ${currentState.email.value}, code: ${currentState.emailCode.value}, token: ${currentState.registerToken.value}")

            val result = authInteractor.confirmEmailAndRegistrationWithCode(
                currentState.email, currentState.emailCode, currentState.registerToken
            )
            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Success register: ${result.data}")
                    _state.update {
                        it.copy(messageForUser = result.data)
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(messageForUser = "Error: ${result.error.message}")
                    }

                    when (result.error) {
                        is ErrorEntity.Auth.EmailCodeConfirmation.FailedToConfirmEmailOrRegisterUser -> {
                            logDebugMessage("FailedToConfirmEmailOrRegisterUser ${result.error.message}")
                        }

                        is ErrorEntity.Auth.EmailCodeConfirmation.InvalidCodeInput -> {
                            logDebugMessage("InvalidCodeInput ${result.error.message}")
                        }

                        is ErrorEntity.Auth.EmailCodeConfirmation.InvalidEmailInput -> {
                            logDebugMessage("InvalidEmailInput ${result.error.message}")
                        }

                        is ErrorEntity.Auth.EmailCodeConfirmation.InvalidToken -> {
                            logDebugMessage("InvalidToken ${result.error.message}")
                        }

                        is ErrorEntity.Auth.EmailCodeConfirmation.WrongConfirmationCode -> {
                            logDebugMessage("WrongConfirmationCode ${result.error.message}")
                        }

                        is ErrorEntity.NetworksError.NoInternet -> {
                            logDebugMessage("NoInternet ${result.error.message}")

                        }

                        else -> logDebugMessage("Something went wrong ${result.error.message}")
                    }

                }
            }
        }
    }

    fun setEmailConfirmCode(code: String) {
        viewModelScope.launch {
            val result = inputValidationInteractor.validateConfirmationCode(code)
            when (result) {

                is ConfirmCodeValidationState.Success -> {
                    _state.update {
                        it.copy(emailCode = result.confirmCode, isEmailCodeValid = true)
                    }
                }

                is ConfirmCodeValidationState.Error -> _state.update {
                    it.copy(emailCode = result.confirmCode, isEmailCodeValid = false)
                }
            }
        }
    }


    fun login() {
        viewModelScope.launch {
            val result = authInteractor.loginByEmail(
                currentState.email,
                currentState.password
            )
            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Message login ${result.data.message}")
                    logDebugMessage("Token login ${result.data.token}")
                    _state.update {
                        it.copy(
                            loginToken = result.data.token,
                            messageForUser = result.data.message
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(messageForUser = "Error: ${result.error.message}")
                    }

                    when (result.error) {
                        is ErrorEntity.Auth.Login.FailedToGenerateTokenOrSendEmail -> {
                            logDebugMessage("FailedToGenerateTokenOrSendEmail ${result.error.message}")
                        }

                        is ErrorEntity.Auth.Login.InvalidEmailOrPassword -> {
                            logDebugMessage("InvalidEmailOrPassword ${result.error.message}")
                        }

                        is ErrorEntity.Auth.Login.InvalidInput -> {
                            logDebugMessage("InvalidInput ${result.error.message}")
                        }

                        is ErrorEntity.Auth.Login.InvalidEmailInput -> {
                            logDebugMessage("InvalidEmailInput ${result.error.message}")
                        }

                        is ErrorEntity.Auth.Login.InvalidPasswordInput -> {
                            logDebugMessage("InvalidPasswordInput ${result.error.message}")
                        }

                        is ErrorEntity.NetworksError.NoInternet -> {
                            logDebugMessage("NoInternet ${result.error.message}")
                        }

                        else -> {
                            logDebugMessage("Something went wrong ${result.error.message}")
                        }
                    }
                }
            }
        }
    }

    fun sendLoginConfirmCode() {
        viewModelScope.launch {
            val result = authInteractor.confirmLoginByEmailWithCode(
                currentState.email,
                currentState.loginCode,
                currentState.loginToken
            )

            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Success login: ${result.data}")
                    _state.update {
                        it.copy(messageForUser = result.data)
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(messageForUser = "Error: ${result.error.message}")
                    }

                    when (result.error) {
                        is ErrorEntity.Auth.LoginCodeConfirmation.InvalidCodeInput -> {
                            logDebugMessage("InvalidCodeInput ${result.error.message}")
                        }

                        is ErrorEntity.Auth.LoginCodeConfirmation.InvalidEmailInput -> {
                            logDebugMessage("InvalidEmailInput ${result.error.message}")
                        }

                        is ErrorEntity.Auth.LoginCodeConfirmation.InvalidRequestPayload -> {
                            logDebugMessage("InvalidRequestPayload ${result.error.message}")
                        }

                        is ErrorEntity.Auth.LoginCodeConfirmation.WrongConfirmationCode -> {
                            logDebugMessage("WrongConfirmationCode ${result.error.message}")
                            logDebugMessage("WrongConfirmationCode remaining attempts ${result.error.remainingAttempts}")
                            logDebugMessage("WrongConfirmationCode lock duration ${result.error.lockDuration}")


                        }

                        is ErrorEntity.NetworksError.NoInternet -> {
                            logDebugMessage("NoInternet ${result.error.message}")
                        }

                        else -> {
                            logDebugMessage("Something went wrong ${result.error.message}")
                        }
                    }
                }
            }
        }
    }

    fun setLoginConfirmCode(code: String) {
        viewModelScope.launch {
            val result = inputValidationInteractor.validateConfirmationCode(code)

            when (result) {
                is ConfirmCodeValidationState.Success -> {
                    _state.update {
                        it.copy(loginCode = result.confirmCode, isLoginCodeValid = true)
                    }
                }

                is ConfirmCodeValidationState.Error -> _state.update {
                    it.copy(loginCode = result.confirmCode, isLoginCodeValid = false)
                }
            }
        }
    }

    fun messageWasShown() {
        _state.update {
            it.copy(messageForUser = EMPTY_MESSAGE)
        }
    }

    companion object {
        const val EMPTY_MESSAGE = ""
    }
}