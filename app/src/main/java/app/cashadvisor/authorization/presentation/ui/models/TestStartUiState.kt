package app.cashadvisor.authorization.presentation.ui.models

data class TestStartUiState(
    val emailIsValid: Boolean = false,
    val emailCodeIsValid: Boolean = false,
    val loginCodeIsValid: Boolean = false,
    val message: String = ""
)