package app.cashadvisor.authorization.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.domain.useCase.GetTokenUseCase
import app.cashadvisor.authorization.presentation.model.StartScreenState
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

    private val state: MutableStateFlow<StartScreenState> = MutableStateFlow(StartScreenState())
    private val currentState: StartScreenState
        get() = state.replayCache.firstOrNull() ?: StartScreenState()

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

    fun navigateToNextScreen() {
        if (currentState.tokenValue == null) {
            viewModelScope.launch {
                loadData()
            }
        } else _uiState.update { currentState ->
            currentState.copy(navigateToNextScreen = true)
        }
    }

    fun navigationProceeded() {
        _uiState.update { currentState ->
            currentState.copy(navigateToNextScreen = false)
        }
    }

    private suspend fun loadData() {
        getTokenUseCase.execute().collect { tokenValue ->
            state.update { currentState ->
                currentState.copy(tokenValue = tokenValue)
            }
            updateUiState()
        }
    }

    private suspend fun updateUiState() {
        state.collect { screenState ->
            if (screenState.tokenValue.isNullOrBlank()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isUserLoggedIn = false, navigateToNextScreen = true
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        isUserLoggedIn = true, navigateToNextScreen = true
                    )
                }
            }
        }
    }

}