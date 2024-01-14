package app.cashadvisor.account.domain.api

import app.cashadvisor.account.domain.models.account.AccountData
import app.cashadvisor.account.domain.models.input.AccountNameInputData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AccountInteractor {
    suspend fun getAccountInfo(): Flow<NetworkResult<AccountData>>
    suspend fun setAccountName(data: AccountNameInputData): Flow<NetworkResult<Boolean>>
}