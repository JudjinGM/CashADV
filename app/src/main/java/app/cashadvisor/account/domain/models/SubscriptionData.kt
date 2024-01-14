package app.cashadvisor.account.domain.models

import java.util.Date

data class SubscriptionData(
    val id: String,
    val userID: String,
    val startDate: Date,
    val endDate: Date,
    val isActive: Boolean,
)

