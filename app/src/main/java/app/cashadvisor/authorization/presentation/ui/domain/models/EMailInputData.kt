package app.cashadvisor.authorization.presentation.ui.domain.models

data class EMailInputData(
    val email: String
) : BaseInputData() {
    override fun isValid(): Boolean {
        return email.matches(Regex(REGEX_PATTERN))
    }

    companion object {
        const val REGEX_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}
