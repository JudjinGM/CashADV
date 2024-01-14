package app.cashadvisor.account.domain.models

data class AnalyticsData(
    val income: AnalyticsElement,
    val expense: AnalyticsElement,
    val wealthFund: AnalyticsElement,
)
