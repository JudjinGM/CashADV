package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.domain.useCase.GetUserAuthenticationStateUseCase
import app.cashadvisor.authorization.domain.useCase.GetUserAuthenticationStateUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    @ViewModelScoped
    fun provideGetUserAuthenticationStateUseCase(): GetUserAuthenticationStateUseCase {
        return GetUserAuthenticationStateUseCaseImpl()
    }
}