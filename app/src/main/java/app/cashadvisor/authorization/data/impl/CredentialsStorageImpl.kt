package app.cashadvisor.authorization.data.impl

import android.content.SharedPreferences
import app.cashadvisor.authorization.data.dto.CredentialsDto
import app.cashadvisor.authorization.domain.api.CredentialsStorage
import com.google.gson.Gson

class CredentialsStorageImpl(
    private val storage: SharedPreferences,
    private val key: String,
    private val gson: Gson
) : CredentialsStorage {

    /**
     * todo подумать, чтобы отнаследоваться от CoroutineScope by CoroutineScope(
     *     Dispatchers.IO +
     *         SupervisorJob() и транслировать key в asSharedFlow()
     */

    override fun saveCredentials(credentials: CredentialsDto) {
        val data = gson.toJson(credentials)
        storage.edit().putString(key, data).apply()
    }

    override fun getCredentials(): CredentialsDto? {
        val data = storage.getString(key, null)
        return if (data != null) {
            gson.fromJson(data, CredentialsDto::class.java)
        } else {
            null
        }
    }

    override fun hasCredentials(): Boolean {
        return storage.contains(key)
    }

    override fun clearCredentials() {
        storage.edit().remove(key).apply()
    }
}