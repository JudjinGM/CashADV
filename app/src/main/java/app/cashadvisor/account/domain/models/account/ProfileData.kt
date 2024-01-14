package app.cashadvisor.account.domain.models.account

data class ProfileData(
    val id: String,
    val username: String,
    val name: String,
    val analytics: AnalyticsData,
    val tracker: TrackerData,
    val accounts: List<AccountData>,
    val connectedAccounts: List<ConectedAccountData>,
    val categories: List<CategoriesData>,
    val operationArchive: OperationData,
    val subscriptionData: SubscriptionData,
)
