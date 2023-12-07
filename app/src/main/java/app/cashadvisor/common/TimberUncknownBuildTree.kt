package app.cashadvisor.common

import android.util.Log
import timber.log.Timber

class TimberUncknownBuildTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Log.d("TimberUncknownBuildTree", "log: $priority $tag $message")
    }
}