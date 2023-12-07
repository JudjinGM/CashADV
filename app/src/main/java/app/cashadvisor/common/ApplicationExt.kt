package app.cashadvisor.common

import androidx.appcompat.app.AppCompatActivity
import app.cashadvisor.BuildConfig
import timber.log.Timber


fun AppCompatActivity.configureTimber() = when (BuildConfig.LOGGING_LEVEL) {
    "DEBUG" -> {
        Timber.plant(Timber.DebugTree())
    }

    "RELEASE" -> {
        Timber.plant(ReleaseTree())
    }

    "QA" -> {
        Timber.plant(Timber.DebugTree())
        Timber.plant(ReleaseTree())
    }

    else -> { Timber.plant(TimberUncknownBuildTree()) }
}