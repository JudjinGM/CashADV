package app.cashadvisor.authorization.presentation.ui.models

data class TestStartUiState(
    val emailCodeIsValid: Boolean = false,
    val loginCodeIsValid: Boolean = false,
    val message: String = ""
)