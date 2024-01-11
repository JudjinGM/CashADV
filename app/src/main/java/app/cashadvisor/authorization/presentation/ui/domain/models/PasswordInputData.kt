package app.cashadvisor.authorization.presentation.ui.domain.models

data class PasswordInputData(
    val password: String
) : BaseInputData() {
    override fun isValid(): Boolean = password.length >= MIN_LENGTH && isCorrectSymbols()

    private fun isCorrectSymbols(): Boolean = password.matches(Regex(REGEX_PATTERN))

    companion object {
        const val MIN_LENGTH = 8
        const val REGEX_PATTERN = "[a-zA-Z0-9_]+"
    }
}

