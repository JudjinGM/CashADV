package app.cashadvisor.common

import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.BuildConfig
import timber.log.Timber



fun AppCompatActivity.configureTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(TimberLogTree())
    }
}