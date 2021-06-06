package com.toloza.avengersapp.data.login

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.toloza.avengersapp.R

class FirebaseAuthBuilder : LoginBuilder {
    override fun build(): Intent {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_AvengersApp)
            .build()
    }
}