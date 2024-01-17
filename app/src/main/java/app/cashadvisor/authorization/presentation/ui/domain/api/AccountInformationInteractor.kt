package app.cashadvisor.authorization.presentation.ui.domain.api

import app.cashadvisor.authorization.presentation.ui.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow

interface AccountInformationInteractor {
    fun getAccountInformation(): Flow<AccountInformation>
}