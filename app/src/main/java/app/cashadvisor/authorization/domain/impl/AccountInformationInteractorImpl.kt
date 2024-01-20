package app.cashadvisor.authorization.domain.impl

import app.cashadvisor.authorization.domain.api.AccountInformationInteractor
import app.cashadvisor.authorization.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class AccountInformationInteractorImpl @Inject constructor() : AccountInformationInteractor {
    override fun getAccountInformation(): Flow<AccountInformation> = flow {
        // todo обновить класс с учетом реальных данных
        emit(MOCK_ACCOUNT_INFORMATION)
    }

    companion object {
        private val MOCK_ACCOUNT_INFORMATION = AccountInformation.Authorized(
            email = "test@test.test",
            token = "123",
            refreshToken = "123",
            isEmailVerified = true,
            tokenValidTill = Date()
        )
    }

}