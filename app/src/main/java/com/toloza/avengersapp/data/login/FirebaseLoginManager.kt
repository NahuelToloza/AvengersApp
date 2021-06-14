package com.toloza.avengersapp.data.login

import com.google.firebase.auth.FirebaseAuth
import com.toloza.avengersapp.data.model.internal.User

class FirebaseLoginManager : LoginManager {
    override fun getBuilder(): LoginBuilder {
        return FirebaseAuthBuilder()
    }

    override fun getUser(): User {
        val user = FirebaseAuth.getInstance().currentUser

        return User(
            mail = user?.email ?: "",
            name = user?.displayName ?: ""
        )
    }
}