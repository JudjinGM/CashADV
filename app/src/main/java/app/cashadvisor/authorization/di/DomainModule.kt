package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.data.dataSource.TokenLocalDataSource
import app.cashadvisor.authorization.data.repositoryImpl.AuthRepositoryImpl
import app.cashadvisor.authorization.domain.repository.AuthRepository
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
    fun provideGetUserAuthenticationStateUseCase(
        authRepository: AuthRepository
    ): GetUserAuthenticationStateUseCase {
        return GetUserAuthenticationStateUseCaseImpl(
            authRepository = authRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        tokenLocalDataSource: TokenLocalDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(tokenLocalDataSource)
    }
}