package app.cashadvisor.authorization.presentation.ui.domain.models

data class PasswordInputData(
    val password: String
) : BaseInputData() {
    // todo возможно стоит возвращать результат валидации вместо булевого значения с указанием на ошибку
    override fun isValid(): Boolean =
        password.length in MIN_LENGTH..MAX_LENGTH && isCorrectSymbols()

    private fun isCorrectSymbols(): Boolean = password.matches(Regex(REGEX_PATTERN))

    companion object {
        const val MIN_LENGTH = 8
        const val MAX_LENGTH = 30
        const val REGEX_PATTERN = "[a-zA-Z0-9_]+" // по условию: цифры, буквы A-z и знак _
    }
}

