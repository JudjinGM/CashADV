package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.data.dataSource.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.dataSource.impl.AuthRemoteDataSourceImpl
import app.cashadvisor.authorization.data.network.AuthApiService
import app.cashadvisor.authorization.data.repositoryImpl.AuthRepositoryImpl
import app.cashadvisor.authorization.domain.AuthExceptionToErrorMapper
import app.cashadvisor.authorization.domain.repository.AuthRepository
import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        @AuthMapper baseExceptionToErrorMapper: BaseExceptionToErrorMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authRemoteDataSource = authRemoteDataSource,
            baseExceptionToErrorMapper = baseExceptionToErrorMapper,
        )
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authApiService: AuthApiService
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authApiService)
    }


    @AuthMapper
    @Provides
    fun provideExceptionToErrorMapper(): BaseExceptionToErrorMapper {
        return AuthExceptionToErrorMapper()
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthMapper
