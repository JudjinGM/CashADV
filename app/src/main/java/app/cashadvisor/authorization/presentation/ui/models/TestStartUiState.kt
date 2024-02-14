package app.cashadvisor.authorization.presentation.ui.models

data class TestStartUiState(
    val emailIsValid: Boolean = false,
    val registerCodeIsValid: Boolean = false,
    val loginCodeIsValid: Boolean = false,
    val message: String = ""
)