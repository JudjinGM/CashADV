package app.cashadvisor.common.di

import app.cashadvisor.authorization.domain.api.AccountInformationInteractor
import app.cashadvisor.authorization.domain.api.CredentialsStorage
import app.cashadvisor.authorization.domain.impl.AccountInformationInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class InteractorModule {

    @Provides
    fun providesAccountInformationInteractor(
        storage: CredentialsStorage,
    ): AccountInformationInteractor = AccountInformationInteractorImpl(storage)

}