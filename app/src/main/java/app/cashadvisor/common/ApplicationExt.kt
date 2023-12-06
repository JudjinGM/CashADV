package app.cashadvisor.common

import androidx.appcompat.app.AppCompatActivity
import app.cashadvisor.R
import com.google.android.datatransport.BuildConfig
import timber.log.Timber


fun AppCompatActivity.configureTimber() = when (BuildConfig.BUILD_TYPE) {
    "debug" -> {
        Timber.plant(Timber.DebugTree())
    }

    "release" -> {
        Timber.plant(ReleaseTree())
    }

    "qa" -> {
        Timber.plant(Timber.DebugTree())
        Timber.plant(ReleaseTree())
    }

    else -> {}
}