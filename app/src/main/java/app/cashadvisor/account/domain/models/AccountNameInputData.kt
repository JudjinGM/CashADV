package app.cashadvisor.account.domain.models

import app.cashadvisor.common.models.BaseInputData

data class AccountNameInputData(
    val name: String
) : BaseInputData() {
    override fun isValid(): Boolean = name.isNotEmpty()
}
