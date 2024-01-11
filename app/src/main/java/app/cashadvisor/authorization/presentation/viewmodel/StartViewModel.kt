package app.cashadvisor.authorization.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.domain.useCase.GetTokenUseCase
import app.cashadvisor.authorization.presentation.ui.model.StartScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<StartScreenUiState> =
        MutableStateFlow(StartScreenUiState())

    val uiState: StateFlow<StartScreenUiState> = _uiState.asStateFlow()

    fun setDebugStatus(isDebug: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isDebug = isDebug
            )
        }
    }

    fun authenticateUser() {
        loadAuthenticationData()
    }

    fun userMessageShown() {
        _uiState.update { currentUiState ->
            currentUiState.copy(userMessage = null)
        }
    }

    private fun loadAuthenticationData() {
        viewModelScope.launch {
            try {
                getTokenUseCase.execute()
                    .collect { tokenValue ->
                        updateUiAuthState(tokenValue)
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

    private fun updateUiAuthState(tokenValue: String?) {
        if (tokenValue.isNullOrBlank()) {
            _uiState.update { currentState ->
                currentState.copy(
                    isUserAuthenticated = true,
                    isAuthenticationSuccessful = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isUserAuthenticated = true,
                    isAuthenticationSuccessful = true
                )
            }
        }
    }

}