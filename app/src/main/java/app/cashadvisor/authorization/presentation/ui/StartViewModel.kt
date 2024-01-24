package app.cashadvisor.authorization.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cashadvisor.authorization.data.model.AuthInputData
import app.cashadvisor.authorization.data.model.Email
import app.cashadvisor.authorization.data.model.Password
import app.cashadvisor.authorization.domain.models.AccountInformation
import app.cashadvisor.authorization.domain.repository.AuthRepository
import app.cashadvisor.common.domain.Resource
import app.cashadvisor.common.domain.model.ErrorEntity
import app.cashadvisor.common.utill.extensions.logDebugMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    //отладочная вьюмодель
    fun fetch() {
        viewModelScope.launch {
            val result = authRepository.loginByEmail(
                AuthInputData(
                    Email("example@example.com"),
                    Password("somepassword")
                )
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

                        else -> logDebugMessage(
                            "Error ${result.error?.message ?: "Unknown else"} "
                        )

                    }
                }

                is Resource.Success -> when (result.data) {
                    is AccountInformation.Authorized -> logDebugMessage("Success Authorized")
                    AccountInformation.NotAuthorized -> logDebugMessage("Success NotAuthorized")
                    else -> logDebugMessage("Success empty/null data")
                }
            }
        }
    }
}