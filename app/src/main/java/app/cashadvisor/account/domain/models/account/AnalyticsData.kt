package app.cashadvisor.account.domain.models.account

data class AnalyticsData(
    val income: AnalyticsElement,
    val expense: AnalyticsElement,
    val wealthFund: AnalyticsElement,
)
