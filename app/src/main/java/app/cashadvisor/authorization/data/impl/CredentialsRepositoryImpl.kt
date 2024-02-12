package app.cashadvisor.authorization.data.impl
import kotlinx.serialization.json.Json
import android.content.SharedPreferences
import app.cashadvisor.authorization.data.dto.CredentialsDto
import app.cashadvisor.authorization.domain.api.CredentialsRepository
import kotlinx.serialization.encodeToString

class CredentialsRepositoryImpl(
    private val storage: SharedPreferences,
    private val key: String,
    private val json: Json
) : CredentialsRepository {

    /**
     * todo подумать, чтобы отнаследоваться от CoroutineScope by CoroutineScope(
     *     Dispatchers.IO +
     *         SupervisorJob() и транслировать key в asSharedFlow()
     */

    override fun saveCredentials(credentials: CredentialsDto) {
        val data = json.encodeToString(credentials)
        storage.edit().putString(key, data).apply()
    }

    override fun getCredentials(): CredentialsDto? {
        val data = storage.getString(key, null)
        return data?.let { json.decodeFromString<CredentialsDto>(it) }
    }

    override fun hasCredentials(): Boolean {
        return storage.contains(key)
    }

    override fun clearCredentials() {
        storage.edit().remove(key).apply()
    }
}