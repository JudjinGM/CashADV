package app.cashadvisor.authorization.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.presentation.viewmodel.models.EntryInteractionState
import app.cashadvisor.authorization.presentation.viewmodel.models.EntryScreenState
import app.cashadvisor.common.ui.BaseViewModel
import app.cashadvisor.common.utils.debounce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor() : BaseViewModel() {
    private var _state = MutableStateFlow<EntryScreenState>(EntryScreenState.Default)
    val state: StateFlow<EntryScreenState>
        get() = _state
    private var coolDownDebounce: ((Unit) -> Unit) = debounce<Unit>(
        COOLDOWN_DELAY,
        viewModelScope,
        false,
        true,
    ) {
        _state.value = EntryScreenState.Default
    }

    fun handleInteraction(action: EntryInteractionState) {
        when (action) {
            is EntryInteractionState.SignUpTapped -> {
                Timber.tag("EntryViewModel").d("Sign up")
                _state.value = EntryScreenState.SignUp
                coolDownDebounce.invoke(Unit)
            }

            is EntryInteractionState.SignInTapped -> {
                Timber.tag("EntryViewModel").d("Sign in")
                _state.value = EntryScreenState.SignIn
                coolDownDebounce.invoke(Unit)
            }

            else -> {
                // no-op
            }
        }
    }

    companion object {
        private const val COOLDOWN_DELAY = 100L
    }
}