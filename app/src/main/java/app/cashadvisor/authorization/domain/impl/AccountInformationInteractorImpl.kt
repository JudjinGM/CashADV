package app.cashadvisor.authorization.domain.impl

import app.cashadvisor.authorization.domain.api.AccountInformationInteractor
import app.cashadvisor.authorization.domain.api.CredentialsStorage
import app.cashadvisor.authorization.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountInformationInteractorImpl(
    private val storage: CredentialsStorage
) : AccountInformationInteractor {
    override fun getAccountInformation(): Flow<AccountInformation> = flow {
        if (storage.hasCredentials()) {
            val data = storage.getCredentials()
            data?.let {
                emit(
                    AccountInformation.Authorized(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                )
            }
        } else {
            emit(AccountInformation.NotAuthorized)
        }
    }

    companion object {
        private val MOCK_ACCOUNT_INFORMATION = AccountInformation.Authorized(
            accessToken = "123",
            refreshToken = "123",
        )
    }

}