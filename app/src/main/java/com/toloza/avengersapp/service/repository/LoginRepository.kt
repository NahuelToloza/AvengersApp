package com.toloza.avengersapp.service.repository

import com.toloza.avengersapp.data.model.User

class LoginRepository {
    //TODO - Return local user information
    fun getLocalUser(): User? = User("maiiil", "NAMEEE")
}
