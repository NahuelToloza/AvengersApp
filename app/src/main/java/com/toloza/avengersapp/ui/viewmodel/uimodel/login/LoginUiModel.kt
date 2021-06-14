package com.toloza.avengersapp.ui.viewmodel.uimodel.login

import com.toloza.avengersapp.data.login.LoginBuilder
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.util.Event

data class LoginUiModel(
    val launchLogin: Event<LoginBuilder>? = null,
    val continueWithFlow: Event<NullModel>? = null,
    val showError: Event<ServerError>? = null
)