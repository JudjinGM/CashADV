package app.cashadvisor.authorization.data.impl

import androidx.security.crypto.EncryptedSharedPreferences
import app.cashadvisor.authorization.data.dto.CredentialsDto
import app.cashadvisor.authorization.domain.api.CredentialsStorage
import com.google.gson.Gson

class CredentialsStorageImpl (
    private val starage: EncryptedSharedPreferences,
    private val key: String,
    private val gson: Gson
): CredentialsStorage {

    /**
     * todo подумать, чтобы отнаследоваться от CoroutineScope by CoroutineScope(
     *     Dispatchers.IO +
     *         SupervisorJob() и транслировать key в asSharedFlow()
     */

    override fun saveCredentials(credentials: CredentialsDto) {
        val data = gson.toJson(credentials)
        starage.edit().putString(key, data).apply()
    }

    override fun getCredentials(): CredentialsDto? {
        val data = starage.getString(key, null)
        return if (data != null) {
            gson.fromJson(data, CredentialsDto::class.java)
        } else {
            null
        }
    }

    override fun hasCredentials(): Boolean {
        return starage.contains(key)
    }

    override fun clearCredentials() {
        starage.edit().remove(key).apply()
    }
}