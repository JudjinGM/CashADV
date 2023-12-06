package app.cashadvisor.common

import android.util.Log.ERROR
import android.util.Log.WARN
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == WARN || priority == ERROR) {
            crashlytics.log("\\$priority \\$tag \\$message")
        }
    }
}