package app.cashadvisor.authorization.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cashadvisor.authorization.data.dataSource.TokenLocalDataSource
import app.cashadvisor.authorization.data.dataSourceImpl.TokenLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideTokenLocalDataSource(
        dataStore: DataStore<Preferences>
    ): TokenLocalDataSource {
        return TokenLocalDataSourceImpl(dataStore = dataStore)
    }

}