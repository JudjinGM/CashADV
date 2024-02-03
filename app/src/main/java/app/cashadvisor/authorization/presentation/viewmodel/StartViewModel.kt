package app.cashadvisor.authorization.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.domain.api.AccountInformationInteractor
import app.cashadvisor.authorization.domain.models.AccountInformation
import app.cashadvisor.authorization.presentation.ui.models.StartScreenUiState
import app.cashadvisor.common.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val accountInformationInteractor: AccountInformationInteractor
) : BaseViewModel() {

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
                accountInformationInteractor.getAccountInformation()
                    .collect { accountInformationState ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                isUserAuthenticated = true,
                                isAuthenticationSuccessful = when (accountInformationState) {
                                    is AccountInformation.Authorized -> true
                                    else -> false
                                }
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        userMessage = app.cashadvisor.uikit.R.string.something_went_wrong
                    )
                }
            }
        }
    }
}