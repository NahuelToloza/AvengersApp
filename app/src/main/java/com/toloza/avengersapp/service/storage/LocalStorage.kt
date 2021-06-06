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
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    suspend inline fun <reified T> get(key: String): T? {
        val value: String?
        withContext(dispatcherProvider.io) {
            //We read JSON String which was saved.
            value = preferences.getString(key, null)
            //JSON String was found which means object can be read.
            //We convert this JSON String to model object. Parameter "c" (of
            //type Class < T >" is used to cast.

        }
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

}
