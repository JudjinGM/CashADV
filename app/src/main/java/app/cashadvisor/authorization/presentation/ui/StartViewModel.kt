package app.cashadvisor.authorization.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.data.model.Email
import app.cashadvisor.authorization.data.model.Password
import app.cashadvisor.authorization.domain.api.AuthInteractor
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.extensions.logDebugMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : ViewModel() {

    //отладочная вьюмодель
    fun fetch() {
        viewModelScope.launch {
            val result = authInteractor.loginByEmail(
                Email("example@example.com"),
                Password("somePassword")
            )

            when (result) {
                is Resource.Error -> {
                    when (result.error) {
                        is ErrorEntity.AuthError.InvalidEmailOrPassword -> {
                            logDebugMessage("Error ${result.error.message}")
                        }

                        is ErrorEntity.NetworksError.NoInternet -> {
                            logDebugMessage("Error ${result.error.message}")
                        }

                        is ErrorEntity.UnknownError -> {
                            logDebugMessage("Error ${result.error.message}")
                        }
                    }
                }

                is Resource.Success -> when (result.data) {
                    true -> logDebugMessage("Success Authorized")
                    false -> logDebugMessage("Success NotAuthorized")
                }
            }
        }
    }
}