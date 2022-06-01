package com.demo.freshworkstudiopractical

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.io.IOException

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // Timber is used to show log
            Timber.plant(Timber.DebugTree())
        }
    }
}