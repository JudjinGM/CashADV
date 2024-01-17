package app.cashadvisor.authorization.presentation.ui.domain.impl

import app.cashadvisor.authorization.presentation.ui.domain.api.AccountInformationInteractor
import app.cashadvisor.authorization.presentation.ui.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class AccountInformationInteractorImpl: AccountInformationInteractor {
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