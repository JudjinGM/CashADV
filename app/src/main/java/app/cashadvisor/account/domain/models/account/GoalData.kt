package app.cashadvisor.account.domain.models

data class GoalData (
    val currentState: Int,
    val goal: String,
    val id: String,
    val need: Int,
    val userId: String,
)