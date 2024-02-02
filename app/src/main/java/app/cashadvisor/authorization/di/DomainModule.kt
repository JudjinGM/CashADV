package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.domain.api.AuthInteractor
import app.cashadvisor.authorization.domain.impl.AuthInteractorImpl
import app.cashadvisor.authorization.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAuthInteractor(
        authRepository: AuthRepository
    ): AuthInteractor {
        return AuthInteractorImpl(authRepository)
    }
}