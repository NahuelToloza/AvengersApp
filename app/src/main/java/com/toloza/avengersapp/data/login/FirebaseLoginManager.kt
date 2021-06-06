package com.toloza.avengersapp.data.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.toloza.avengersapp.data.model.User

class FirebaseLoginManager : LoginManager {
    override fun getBuilder(): LoginBuilder {
        return FirebaseAuthBuilder()
    }

    override fun getUser(): User {
        val user = FirebaseAuth.getInstance().currentUser
        //TODO BORRAR LOG
        Log.d("Email pepe", user?.email ?: "")
        Log.d("Nombre pepe", user?.displayName ?: "")

        return User(
            mail = user?.email ?: "",
            name = user?.displayName ?: ""
        )
    }
}