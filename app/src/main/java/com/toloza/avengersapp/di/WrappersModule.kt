package com.toloza.avengersapp.di

import com.toloza.avengersapp.data.mapper.CharacterMapper
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.login.FirebaseLoginManager
import com.toloza.avengersapp.data.login.LoginManager
import com.toloza.avengersapp.data.mapper.ComicMapper
import com.toloza.avengersapp.data.mapper.ComicsAdapterModelMapper
import com.toloza.avengersapp.data.mapper.EventMapper
import com.toloza.avengersapp.service.error.HandleHttpErrors
import com.toloza.avengersapp.service.repository.AvengersRepository
import com.toloza.avengersapp.service.repository.LoginRepository
import com.toloza.avengersapp.service.security.AvengersHashKeyBuilder
import com.toloza.avengersapp.service.security.HashKeyBuilder
import com.toloza.avengersapp.service.storage.LocalStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val wrappersModule = module {
    single { CoroutinesDispatcherProvider() }

    single { LocalStorage(get(), get()) }
    single<LoginManager> { FirebaseLoginManager() }
    single<HashKeyBuilder> { AvengersHashKeyBuilder() }

    single { ComicMapper() }
    single { CharacterMapper(get()) }
    single { EventMapper(get()) }
    single { ComicsAdapterModelMapper() }

    single { HandleHttpErrors(androidContext()) }

    single { LoginRepository(get()) }
    single { AvengersRepository(get()) }
}