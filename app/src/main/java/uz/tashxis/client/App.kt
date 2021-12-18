package uz.tashxis.client

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        context = this
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }
}