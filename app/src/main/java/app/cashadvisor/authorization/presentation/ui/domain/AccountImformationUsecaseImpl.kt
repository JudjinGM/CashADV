package app.cashadvisor.authorization.presentation.ui.domain

import app.cashadvisor.authorization.presentation.ui.domain.api.AccountInformationUsecase
import app.cashadvisor.authorization.presentation.ui.domain.models.AccountInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class AccountInformationUsecaseImpl: AccountInformationUsecase {
    override suspend fun getAccountInformation(): Flow<AccountInformation> = flow {
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