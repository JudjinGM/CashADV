package app.cashadvisor.authorization.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.domain.api.InputValidationInteractor
import app.cashadvisor.authorization.domain.api.LoginInteractor
import app.cashadvisor.authorization.domain.api.RegisterInteractor
import app.cashadvisor.authorization.domain.models.states.ConfirmCodeValidationState
import app.cashadvisor.authorization.domain.models.states.EmailValidationState
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
    private val registerInteractor: RegisterInteractor,
    private val loginInteractor: LoginInteractor,
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
                        emailIsValid = state.isEmailValid,
                        emailCodeIsValid = state.isEmailCodeValid && state.isRegisterInProgress,
                        loginCodeIsValid = state.isLoginCodeValid && state.isLoginInProgress,
                        message = state.messageForUser
                    )
                }

            }
        }
    }

    fun setEmail(email: String) {
        viewModelScope.launch {
            val result = inputValidationInteractor.validateEmail(email)
            when (result) {
                is EmailValidationState.Success -> {
                    _state.update {
                        it.copy(email = result.email, isEmailValid = true)
                    }
                }

                is EmailValidationState.Error -> {
                    _state.update {
                        it.copy(email = result.email, isEmailValid = false)
                    }
                }
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            registerInteractor.isRegisterInProgress().collect { isInProgress ->
                _state.update {
                    it.copy(
                        isRegisterInProgress = isInProgress
                    )
                }
            }
        }
        viewModelScope.launch {
            val result = registerInteractor.registerByEmail(
                currentState.email,
                currentState.password
            )

            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Message register ${result.data.message}")
                    _state.update {
                        it.copy(
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

                        is ErrorEntity.Register -> {
                            when (result.error) {
                                is ErrorEntity.Register.FailedToGenerateTokenOrSendEmail -> {
                                    logDebugMessage("FailedToGenerateTokenOrSendEmail ${result.error.message}")
                                }

                                is ErrorEntity.Register.InvalidEmail -> {
                                    logDebugMessage("InvalidEmail ${result.error.message}")

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
            logDebugMessage("email: ${currentState.email.value}, code: ${currentState.emailCode.value}")

            val result = registerInteractor.confirmEmailAndRegistrationWithCode(
                currentState.email, currentState.emailCode
            )
            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Success register: ${result.data}")
                    _state.update {
                        it.copy(
                            messageForUser = result.data,
                            isRegisterInProgress = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            messageForUser = "Error: ${result.error.message}",
                            isRegisterInProgress = false
                        )
                    }

                    when (result.error) {
                        is ErrorEntity.RegisterByEmailConfirmationWithCode.FailedToConfirmRegisterByEmailOrRegisterUserWithCode -> {
                            logDebugMessage("FailedToConfirmEmailOrRegisterUser ${result.error.message}")
                        }

                        is ErrorEntity.RegisterByEmailConfirmationWithCode.InvalidToken -> {
                            logDebugMessage("InvalidToken ${result.error.message}")
                        }

                        is ErrorEntity.RegisterByEmailConfirmationWithCode.WrongWithCodeConfirmationRegisterBy -> {
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
            loginInteractor.isLoginInProgress().collect { isInProgress ->
                _state.update {
                    it.copy(
                        isLoginInProgress = isInProgress
                    )
                }
            }
        }

        viewModelScope.launch {
            val result = loginInteractor.loginByEmail(
                currentState.email,
                currentState.password
            )
            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Message login ${result.data.message}")
                    _state.update {
                        it.copy(
                            messageForUser = result.data.message,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            messageForUser = "Error: ${result.error.message}",
                        )
                    }

                    when (result.error) {
                        is ErrorEntity.Login.FailedToGenerateTokenOrSendEmail -> {
                            logDebugMessage("FailedToGenerateTokenOrSendEmail ${result.error.message}")
                        }

                        is ErrorEntity.Login.InvalidEmailOrPassword -> {
                            logDebugMessage("InvalidEmailOrPassword ${result.error.message}")
                        }

                        is ErrorEntity.Login.InvalidInput -> {
                            logDebugMessage("InvalidInput ${result.error.message}")
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
            val result = loginInteractor.confirmLoginByEmailWithCode(
                currentState.email,
                currentState.loginCode,
            )

            when (result) {
                is Resource.Success -> {
                    logDebugMessage("Success login: ${result.data}")
                    _state.update {
                        it.copy(
                            messageForUser = result.data,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            messageForUser = "Error: ${result.error.message}",
                        )
                    }

                    when (result.error) {


                        is ErrorEntity.LoginByEmailConfirmationWithCode.InvalidRequestPayload -> {
                            logDebugMessage("InvalidRequestPayload ${result.error.message}")
                        }

                        is ErrorEntity.LoginByEmailConfirmationWithCode.WrongWithCodeConfirmationByEmail -> {
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