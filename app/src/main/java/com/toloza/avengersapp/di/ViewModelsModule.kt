package com.toloza.avengersapp.di

import com.toloza.avengersapp.ui.viewmodel.CharactersViewModel
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationViewModel
import com.toloza.avengersapp.ui.viewmodel.LoginViewModel
import com.toloza.avengersapp.ui.viewmodel.MainViewModel
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
}