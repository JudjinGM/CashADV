package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.data.AuthDataMapper
import app.cashadvisor.authorization.data.NetworkToAuthExceptionMapper
import app.cashadvisor.authorization.data.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.impl.AuthRemoteDataSourceImpl
import app.cashadvisor.authorization.data.api.AuthApiService
import app.cashadvisor.authorization.data.impl.AuthRepositoryImpl
import app.cashadvisor.authorization.domain.AuthDomainMapper
import app.cashadvisor.authorization.domain.AuthExceptionToErrorMapper
import app.cashadvisor.authorization.domain.api.AuthRepository
import app.cashadvisor.common.domain.BaseExceptionToErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
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
        @AuthExceptionMapper baseExceptionToErrorMapper: BaseExceptionToErrorMapper,
        authDomainMapper: AuthDomainMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authRemoteDataSource = authRemoteDataSource,
            exceptionToErrorMapper = baseExceptionToErrorMapper,
            authDomainMapper = authDomainMapper
        )
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authApiService: AuthApiService,
        exceptionMapper: NetworkToAuthExceptionMapper,
        authDataMapper: AuthDataMapper
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authApiService, exceptionMapper, authDataMapper)
    }

    @Provides
    @Singleton
    fun provideNetworkToAuthExceptionMapper(
        json: Json
    ): NetworkToAuthExceptionMapper {
        return NetworkToAuthExceptionMapper(json)
    }

    @AuthExceptionMapper
    @Provides
    fun provideExceptionToErrorMapper(): BaseExceptionToErrorMapper {
        return AuthExceptionToErrorMapper()
    }

    @Provides
    @Singleton
    fun provideAuthDataMapper(): AuthDataMapper {
        return AuthDataMapper()
    }

    @Provides
    @Singleton
    fun provideAuthDomainMapper(): AuthDomainMapper {
        return AuthDomainMapper()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthExceptionMapper
}



