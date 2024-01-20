package app.cashadvisor.authorization.di


import app.cashadvisor.authorization.domain.api.AccountInformationInteractor
import app.cashadvisor.authorization.domain.impl.AccountInformationInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindAccountInformationInteractor(
        accountInformationInteractorImpl: AccountInformationInteractorImpl
    ): AccountInformationInteractor
}