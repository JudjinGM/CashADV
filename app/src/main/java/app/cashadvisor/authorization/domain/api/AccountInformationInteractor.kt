package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow

interface AccountInformationInteractor {
    fun getAccountInformation(): Flow<AccountInformation>
}