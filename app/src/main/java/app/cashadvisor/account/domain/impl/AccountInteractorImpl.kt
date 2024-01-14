package app.cashadvisor.account.domain.impl

import app.cashadvisor.account.domain.api.AccountInteractor
import app.cashadvisor.account.domain.models.account.AccountData
import app.cashadvisor.account.domain.models.account.AnalyticsData
import app.cashadvisor.account.domain.models.account.AnalyticsElement
import app.cashadvisor.account.domain.models.account.FinHealthData
import app.cashadvisor.account.domain.models.account.OperationData
import app.cashadvisor.account.domain.models.account.ProfileData
import app.cashadvisor.account.domain.models.account.SubscriptionData
import app.cashadvisor.account.domain.models.account.TrackerData
import app.cashadvisor.account.domain.models.account.TrackingStateData
import app.cashadvisor.account.domain.models.input.AccountNameInputData
import app.cashadvisor.common.models.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class AccountInteractorImpl : AccountInteractor {
    override suspend fun getAccountInfo(): Flow<NetworkResult<AccountData>> = flow {
        // todo подключить репозиторий для получения данных с сервера и убрать корутину =flow из сигнатуры
        emit(NetworkResult.Success(AccountData("message", MOCK_PROFILE)))

    }

    override suspend fun setAccountName(data: AccountNameInputData): Flow<NetworkResult<Boolean>> =
        flow {
            emit(NetworkResult.Success(true))
        }

    override fun isAccountNameValid(data: AccountNameInputData): Boolean = data.isValid()

    companion object {
        private val MOCK_PROFILE = ProfileData(
            id = "id",
            username = "username",
            name = "name",
            analytics = AnalyticsData(
                income = AnalyticsElement(
                    id = "id",
                    amount = 0.0,
                    categoryId = "categoryId",
                    date = Date(),
                    planed = true,
                    userId = "userId",
                ),
                expense = AnalyticsElement(
                    id = "id",
                    amount = 0.0,
                    categoryId = "categoryId",
                    date = Date(),
                    planed = true,
                    userId = "userId",
                ),
                wealthFund = AnalyticsElement(
                    id = "id",
                    amount = 0.0,
                    categoryId = "categoryId",
                    date = Date(),
                    planed = true,
                    userId = "userId",
                ),
            ),
            tracker = TrackerData(
                trackingState = TrackingStateData(
                    state = 0,
                    userId = "userId",
                ),
                goals = emptyList(),
                finHealth = FinHealthData(
                    id = "id",
                    incomeScore = 0,
                    expenseScore = 0,
                    investmentScore = 0,
                    obligationScore = 0,
                    planScore = 0,
                    totalScore = 0,
                    userId = "userId",
                ),
            ),
            accounts = emptyList(),
            connectedAccounts = emptyList(),
            categories = emptyList(),
            operationArchive = OperationData(
                id = "id",
                descriprion = "descriprion",
                amount = 0.0,
                date = Date(),
                category = "category",
                type = "type",
            ),
            subscriptionData = SubscriptionData(
                id = "id",
                userID = "userID",
                startDate = Date(),
                endDate = Date(),
                isActive = true,
            ),
        )
    }

}