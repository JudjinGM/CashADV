package app.cashadvisor.common

import android.util.Log
import com.google.android.datatransport.BuildConfig
import timber.log.Timber

class TimberLogTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Log.println(priority, tag, message)

//        when {
//            BuildConfig.DEBUG -> {
//                // Логгирование в Logcat для Debug и QA
//                Log.println(priority, tag, message)
//            }

//             BuildConfig.RELEASE -> {
//                // Логгирование в Crashlytics для QA и Release
//                if (priority == Log.WARN || priority == Log.ERROR) {
//                    // Здесь код для отправки логов в Crashlytics
//                }
//            }
//            else -> {
//                // Логгирование в Logcat для Release
//                Log.println(priority, tag, message)
//            }
//        }
    }
}