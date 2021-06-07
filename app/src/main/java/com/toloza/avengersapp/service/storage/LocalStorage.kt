package com.toloza.avengersapp.service.storage

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import kotlinx.coroutines.withContext

class LocalStorage(
    val preferences: SharedPreferences,
    val dispatcherProvider: CoroutinesDispatcherProvider
) {
    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    suspend fun <T> put(`object`: T, key: String) = withContext(dispatcherProvider.io) {
        val jsonString = GsonBuilder().create().toJson(`object`)

        preferences.edit().putString(key, jsonString).commit()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    suspend inline fun <reified T> get(key: String): T? {
        val value: String?
        withContext(dispatcherProvider.io) {
            value = preferences.getString(key, null)
        }
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

}
