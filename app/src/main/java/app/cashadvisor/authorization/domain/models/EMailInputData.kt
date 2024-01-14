package app.cashadvisor.authorization.domain.models

data class EMailInputData(
    val email: String
) : BaseInputData() {
    // todo возможно стоит возвращать результат валидации вместо булевого значения с указанием на ошибку

    override fun isValid(): Boolean = isCorrectLength() && email.matches(Regex(REGEX_PATTERN))

    private fun isCorrectLength(): Boolean = email.length in MIN_LENGHT..MAX_LENGHT

    companion object {
        const val MIN_LENGHT = 7
        const val MAX_LENGHT = 114
        const val REGEX_PATTERN =
            "(?!.*\\[_.-\\]{2,})(?!.*\\[_.-\\]\$)(?!^\\[-_.\\])\\[a-zA-Z0-9._-\\]{1,49}\\[a-zA-Z0-9\\]@(?!-)\\[a-zA-Z0-9-\\]{1,63}(\\.\\[a-zA-Z\\]{2,})+\$"
    }
}
