package app.cashadvisor.authorization.data.dataSourceImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import app.cashadvisor.authorization.data.dataSource.TokenLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TokenLocalDataSourceImpl
@Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenLocalDataSource {

    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)

    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

    override fun getAccessToken(): Flow<String> {
        val accessTokenFlow: Flow<String> = dataStore.data
            .map { preferences ->
                preferences[accessTokenKey] ?: BLANC_STRING
            }
        return accessTokenFlow
    }

    override fun getRefreshToken(): Flow<String> {
        val refreshTokenFlow: Flow<String> = dataStore.data
            .map { preferences ->
                preferences[refreshTokenKey] ?: BLANC_STRING
            }
        return refreshTokenFlow
    }

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { data ->
            data[accessTokenKey] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { data ->
            data[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun clearAccessToken() {
        dataStore.edit { data ->
            data[accessTokenKey] = BLANC_STRING
        }
    }

    override suspend fun clearRefreshToken() {
        dataStore.edit { data ->
            data[accessTokenKey] = BLANC_STRING
        }
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token_key"
        const val REFRESH_TOKEN_KEY = "refresh_token_key"
        const val BLANC_STRING = ""
    }

}