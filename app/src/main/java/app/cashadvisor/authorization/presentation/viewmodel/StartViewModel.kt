package app.cashadvisor.authorization.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.AuthException
import app.cashadvisor.authorization.domain.useCase.GetRefreshTokenStateUseCase
import app.cashadvisor.authorization.domain.useCase.GetUserAuthenticationStateUseCase
import app.cashadvisor.authorization.presentation.ui.model.StartScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getUserAuthenticationStateUseCase: GetUserAuthenticationStateUseCase,
    private val getRefreshTokenStateUseCase: GetRefreshTokenStateUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<StartScreenUiState> =
        MutableStateFlow(StartScreenUiState())

    val uiState: StateFlow<StartScreenUiState> = _uiState.asStateFlow()

    init {
        setDebugStatus(app.cashadvisor.BuildConfig.DEBUG)
    }

    fun authenticateUser() {
        loadAuthenticationData()
    }

    fun userMessageShown() {
        _uiState.update { currentUiState ->
            currentUiState.copy(userMessage = null)
        }
    }

    fun userProceededNext() {
        _uiState.update { currentUiState ->
            currentUiState.copy(isUserAuthenticated = false)
        }
    }

    private fun setDebugStatus(isDebug: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isDebug = isDebug)
        }
    }

    private fun loadAuthenticationData() {
        viewModelScope.launch {
            try {
                getUserAuthenticationStateUseCase().zip(
                    getRefreshTokenStateUseCase()
                ) { isUserAuthenticated, isRefreshTokenExist ->
                    isUserAuthenticated && isRefreshTokenExist
                }.collect {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(isUserAuthenticated = it)
                    }
                }
            } catch (e: Throwable) {
                handleThrowable(e)
            }
        }
    }

    private fun handleThrowable(throwable: Throwable) {
        when (throwable) {
            is AuthException -> {
                when (throwable) {
                    is AuthException.AccessTokenExceptionOne -> TODO()
                    is AuthException.AccessTokenExceptionThree -> TODO()
                    is AuthException.AccessTokenExceptionTwo -> TODO()
                    is AuthException.RefreshTokenExceptionOne -> TODO()
                    is AuthException.RefreshTokenExceptionThree -> TODO()
                    is AuthException.RefreshTokenExceptionTwo -> TODO()
                    else -> TODO()
                }
            }

            else -> {
                TODO()
            }
        }
    }
}
