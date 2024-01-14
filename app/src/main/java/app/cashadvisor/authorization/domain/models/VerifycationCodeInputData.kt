package app.cashadvisor.authorization.domain.models

import app.cashadvisor.common.models.BaseInputData

data class VerifycationCodeInputData(
    val code: String
) : BaseInputData() {
    // todo возможно стоит возвращать результат валидации вместо булевого значения с указанием на ошибку

    override fun isValid(): Boolean = (code.length == CODE_LENGTH) && isNumbersOnly()

    private fun isNumbersOnly(): Boolean = code.matches(Regex(REGEX_PATTERN))

    companion object {
        const val CODE_LENGTH = 4
        const val REGEX_PATTERN = "[0-9]+" // todo уточнить требования символам в коде
    }
}

