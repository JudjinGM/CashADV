package app.cashadvisor.authorization.presentation.ui.model

data class StartScreenUiState(
    val isDebug: Boolean = false,
    val isUserAuthenticated: Boolean = false,
    val isAuthenticationSuccessful: Boolean = false,
    val userMessage: Int? = null
)