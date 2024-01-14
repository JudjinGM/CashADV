package app.cashadvisor.account.domain.models.account

data class FinHealthData(
    val id: String,
    val incomeScore: Int,
    val expenseScore: Int,
    val investmentScore: Int,
    val obligationScore: Int,
    val planScore: Int,
    val totalScore: Int,
    val userId: String,
)
