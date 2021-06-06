package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.toloza.avengersapp.service.repository.LoginRepository

class MainViewModel(private val repository: LoginRepository): ViewModel() {

    fun setUpUserInformation() {
        val localUser = repository.getLocalUser()
        localUser?.let {
            //todo emit continue flujo
        } ?: run {
            //todo emit launch login
        }
    }
}