package com.toloza.avengersapp

import android.app.Application
import com.facebook.appevents.AppEventsLogger
import com.toloza.avengersapp.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setUp()
    }

    private fun setUp() {
        //Setup Login
        AppEventsLogger.activateApp(this)

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(AppModule.invoke())
        }
    }
}