package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow

interface AccountInformationInteractor {
    suspend fun getAccountInformation(): Flow<AccountInformation>
}