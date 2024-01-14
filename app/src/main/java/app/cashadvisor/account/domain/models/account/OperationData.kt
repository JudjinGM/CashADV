package app.cashadvisor.account.domain.models

import java.util.Date

data class OperationData(
    val id: String,
    val descriprion: String,
    val amount: Double,
    val date: Date,
    val category: String,
    val type: String,
)
