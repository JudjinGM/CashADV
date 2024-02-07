package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.domain.api.AuthInteractor
import app.cashadvisor.authorization.domain.impl.AuthInteractorImpl
import app.cashadvisor.authorization.domain.api.AuthRepository
import app.cashadvisor.authorization.domain.api.InputValidationInteractor
import app.cashadvisor.authorization.domain.impl.InputValidationInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAuthInteractor(
        authRepository: AuthRepository,
        inputValidationInteractor: InputValidationInteractor
    ): AuthInteractor {
        return AuthInteractorImpl(authRepository, inputValidationInteractor)
    }

    @Provides
    fun provideInputValidationInteractor(
        authRepository: AuthRepository
    ): InputValidationInteractor {
        return InputValidationInteractorImpl()
    }
}