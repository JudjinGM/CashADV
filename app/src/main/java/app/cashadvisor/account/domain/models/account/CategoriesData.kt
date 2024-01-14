package app.cashadvisor.account.domain.models.account

data class CategoriesData(
    val expenseCategories: List<NameWithIcon>,
    val incomeCategories: List<NameWithIcon>,
    val investmentCategories: List<NameWithIcon>,
)
