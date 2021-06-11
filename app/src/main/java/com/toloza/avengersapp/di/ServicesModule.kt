package com.toloza.avengersapp.di

import android.app.Application
import android.content.SharedPreferences
import com.toloza.avengersapp.service.api.AvengersApi
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://gateway.marvel.com"
private const val SHARED_PREFERENCES_DEFAULT_NAME = "default"

val servicesModule = module {
    factory { provideOkHttpClient() }

    single { provideRetrofit(get()).create(AvengersApi::class.java) }

    single {
        getSharedPrefs(androidApplication())
    }
}

private fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        SHARED_PREFERENCES_DEFAULT_NAME,
        android.content.Context.MODE_PRIVATE
    )
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()
}