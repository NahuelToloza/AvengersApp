package com.toloza.avengersapp.data.login

import com.toloza.avengersapp.data.model.internal.User

interface LoginManager {
    fun getBuilder(): LoginBuilder

    fun getUser(): User
}