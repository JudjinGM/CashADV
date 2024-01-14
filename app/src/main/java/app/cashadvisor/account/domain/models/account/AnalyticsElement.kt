package app.cashadvisor.account.domain.models

import java.util.Date

data class AnalyticsElement(
    val id: String,
    val amount: Double,
    val categoryId: String? = null,
    val date: Date,
    val planed: Boolean,
    val userId: String,
)
