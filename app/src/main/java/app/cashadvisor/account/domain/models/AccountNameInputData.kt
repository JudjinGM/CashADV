package app.cashadvisor.account.domain.models

import app.cashadvisor.authorization.domain.models.BaseInputData

data class AccountNameInputData(
    val name: String
) : BaseInputData() {
    override fun isValid(): Boolean = name.isNotEmpty()
}
