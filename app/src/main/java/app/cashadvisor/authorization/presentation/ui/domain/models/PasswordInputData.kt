package app.cashadvisor.authorization.presentation.ui.domain.models

data class PasswordInputData(
    val password: String
) : BaseInputData() {
    override fun isValid(): Boolean {
        return password.length >= MIN_LENGTH
    }

    companion object {
        const val MIN_LENGTH = 8
    }
}

