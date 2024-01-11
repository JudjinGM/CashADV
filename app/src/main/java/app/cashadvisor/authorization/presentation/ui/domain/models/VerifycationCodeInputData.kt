package app.cashadvisor.authorization.presentation.ui.domain.models

data class VerifycationCodeInputData(
    val code: String
) : BaseInputData() {
    override fun isValid(): Boolean {
        return (code.length == CODE_LENGTH) && isNumbersOnly()
    }
    private fun isNumbersOnly(): Boolean {
        return code.matches(Regex(REGEX_PATTERN))
    }

    companion object {
        const val CODE_LENGTH = 4
        const val REGEX_PATTERN = "[0-9]+"
    }
}

