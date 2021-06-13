package com.toloza.avengersapp.di

import com.toloza.avengersapp.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        LoginViewModel(
            repository = get(),
            dispatcherProvider = get(),
            loginManager = get()
        )
    }

    viewModel { LoginCommunicationViewModel() }

    viewModel { MainViewModel(get()) }

    viewModel { CharactersViewModel(get(), get(), get()) }

    viewModel { EventsViewModel(get(), get()) }
}