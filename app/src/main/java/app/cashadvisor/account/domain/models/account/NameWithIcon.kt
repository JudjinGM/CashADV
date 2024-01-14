package app.cashadvisor.account.domain.models.account

data class NameWithIcon(
    val id: String,
    val name: String,
    val icon: String,
    val isConstant: Boolean,
    val userId: String,
)
