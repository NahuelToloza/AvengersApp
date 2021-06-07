package com.toloza.avengersapp.di

import android.app.Application
import android.content.SharedPreferences
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.login.FirebaseLoginManager
import com.toloza.avengersapp.data.login.LoginManager
import com.toloza.avengersapp.service.repository.LoginRepository
import com.toloza.avengersapp.service.storage.LocalStorage
import com.toloza.avengersapp.ui.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class AppModule {
    companion object {
        private const val SHARED_PREFERENCES_DEFAULT_NAME = "default"

        operator fun invoke(): Module {
            return module {

                single {
                    getSharedPrefs(androidApplication())
                }
                single { CoroutinesDispatcherProvider() }
                single { LocalStorage(get(), get()) }
                single { LoginRepository(get()) }
                single<LoginManager> { FirebaseLoginManager() }

                viewModel {
                    LoginViewModel(
                        repository = get(),
                        dispatcherProvider = get(),
                        loginManager = get()
                    )
                }
            }
        }

        private fun getSharedPrefs(androidApplication: Application): SharedPreferences {
            return androidApplication.getSharedPreferences(
                SHARED_PREFERENCES_DEFAULT_NAME,
                android.content.Context.MODE_PRIVATE
            )
        }
    }
}