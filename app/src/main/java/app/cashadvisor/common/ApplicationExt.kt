package app.cashadvisor.common

import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.BuildConfig
import timber.log.Timber



fun AppCompatActivity.configureTimber() {
    when (BuildConfig.BUILD_TYPE) {
        "debug" -> {}
        "release" -> {}
        "qa" -> {}
        else -> {}
    }
}