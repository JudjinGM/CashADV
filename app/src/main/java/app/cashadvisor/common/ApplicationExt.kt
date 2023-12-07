package app.cashadvisor.common

import androidx.appcompat.app.AppCompatActivity
import app.cashadvisor.BuildConfig
import timber.log.Timber


fun AppCompatActivity.configureTimber() = when (BuildConfig.LOGGING_LEVEL) {
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

    else -> { Timber.plant(TimberUncknownBuildTree()) }
}