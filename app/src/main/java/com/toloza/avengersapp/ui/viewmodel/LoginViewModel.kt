package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.login.LoginBuilder
import com.toloza.avengersapp.data.login.LoginManager
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.extensions.getGenericError
import com.toloza.avengersapp.service.repository.LoginRepository
import com.toloza.avengersapp.ui.viewmodel.uimodel.login.LoginUiModel
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val repository: LoginRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val loginManager: LoginManager
) : ViewModel() {
    private val _uiState = MutableLiveData<LoginUiModel>()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    fun setUpUserInformation() = viewModelScope.launch(dispatcherProvider.computation) {
        val localUser = repository.getLocalUser()
        localUser?.let {
            emitUiModel(continueWithFlow = Event(NullModel()))
        } ?: run {
            emitUiModel(launchLogin = Event(loginManager.getBuilder()))
        }
    }

    fun handleSuccessLogin() = viewModelScope.launch(dispatcherProvider.computation) {
        repository.saveLocalUser(loginManager.getUser())
        emitUiModel(continueWithFlow = Event(NullModel()))
    }

    fun handleFailedLogin(message: String) = viewModelScope.launch(dispatcherProvider.computation) {
        emitUiModel(showError = Event(getGenericError(message)))
    }

    private suspend fun emitUiModel(
        launchLogin: Event<LoginBuilder>? = null,
        continueWithFlow: Event<NullModel>? = null,
        showError: Event<ServerError>? = null
    ) = withContext(dispatcherProvider.main) {
        _uiState.value = LoginUiModel(
            launchLogin = launchLogin,
            continueWithFlow = continueWithFlow,
            showError = showError
        )
    }
}