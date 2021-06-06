package com.toloza.avengersapp.service.repository

import com.toloza.avengersapp.data.model.User
import com.toloza.avengersapp.service.storage.LocalStorage

class LoginRepository(
    private val localStorage: LocalStorage
) {
    suspend fun getLocalUser(): User? = localStorage.get<User>(USER)

    suspend fun saveLocalUser(user: User) {
        localStorage.put(user, USER)
    }

    companion object{
        const val USER = "USER"
    }
}
