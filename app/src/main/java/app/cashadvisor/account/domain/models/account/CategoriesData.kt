package app.cashadvisor.account.domain.models

data class CategoriesData(
    val expenseCategories: List<NameWithIcon>,
    val incomeCategories: List<NameWithIcon>,
    val investmentCategories: List<NameWithIcon>,
)
