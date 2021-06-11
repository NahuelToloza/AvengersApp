package com.toloza.avengersapp

import android.app.Application
import com.facebook.appevents.AppEventsLogger
import com.toloza.avengersapp.di.servicesModule
import com.toloza.avengersapp.di.viewModelsModule
import com.toloza.avengersapp.di.wrappersModule
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

        //Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)

            val moduleList = listOf(
                wrappersModule,
                servicesModule,
                viewModelsModule
            )
            modules(moduleList)
        }
    }
}