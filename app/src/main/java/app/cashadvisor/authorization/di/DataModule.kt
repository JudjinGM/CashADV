package app.cashadvisor.authorization.di

import app.cashadvisor.authorization.data.dataSource.api.AuthRemoteDataSource
import app.cashadvisor.authorization.data.dataSource.impl.AuthRemoteDataSourceImpl
import app.cashadvisor.authorization.data.dataSource.api.TokenLocalDataSource
import app.cashadvisor.authorization.data.network.AuthApiService
import app.cashadvisor.authorization.data.repositoryImpl.AuthRepositoryImpl
import app.cashadvisor.authorization.domain.ExceptionToErrorMapperAuth
import app.cashadvisor.authorization.domain.repository.AuthRepository
import app.cashadvisor.common.domain.ExceptionToErrorMapperBase
import app.cashadvisor.common.utill.extensions.logDebugMessage
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
        @AuthMapper exceptionToErrorMapperBase: ExceptionToErrorMapperBase
    ): AuthRepository {
        return AuthRepositoryImpl(
            authRemoteDataSource = authRemoteDataSource,
            exceptionToErrorMapperBase = exceptionToErrorMapperBase,
            //TODO: создаю анонимный класс, пока нет реализации, надо будет заменить
            tokenLocalDataSource = object : TokenLocalDataSource {
                override suspend fun saveAccessToken(accessToken: String) {
                    logDebugMessage("access token saved")
                }

                override suspend fun saveRefreshToken(refreshToken: String) {
                    logDebugMessage("refresh token saved")
                }
            }
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
    fun provideExceptionToErrorMapper(): ExceptionToErrorMapperBase {
        return ExceptionToErrorMapperAuth()
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthMapper
