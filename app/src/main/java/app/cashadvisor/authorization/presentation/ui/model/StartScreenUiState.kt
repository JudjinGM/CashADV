package app.cashadvisor.authorization.presentation.ui.model

data class StartScreenUiState(
    val isDebug: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val navigateToNextScreen: Boolean = false
)