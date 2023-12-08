package app.cashadvisor.common

import android.util.Log.ERROR
import android.util.Log.WARN
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == WARN || priority == ERROR
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            WARN -> {crashlytics.log("$priority $tag $message")
                crashlytics.recordException(t ?: Exception(message))
            }
            ERROR -> crashlytics.recordException(t ?: Exception(message))
        }
    }
}