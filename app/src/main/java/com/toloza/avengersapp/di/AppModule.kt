package com.toloza.avengersapp.di

import com.toloza.avengersapp.ui.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class AppModule {
    companion object{
        operator fun invoke(): Module {
            return module {

                viewModel { MainViewModel() }
            }
        }
    }
}