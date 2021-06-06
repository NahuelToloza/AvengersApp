package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.login.LoginBuilder
import com.toloza.avengersapp.data.login.LoginManager
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.service.repository.LoginRepository
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: LoginRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val loginManager: LoginManager
) : ViewModel() {
    private val _uiModel = MutableLiveData<MainUiModel>()
    val uiModel: LiveData<MainUiModel>
        get() = _uiModel

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
    }

    fun handleFailedLogin() {

    }

    private suspend fun emitUiModel(
        launchLogin: Event<LoginBuilder>? = null,
        continueWithFlow: Event<NullModel>? = null
    ) = withContext(dispatcherProvider.main) {
        _uiModel.value = MainUiModel(
            launchLogin = launchLogin,
            continueWithFlow = continueWithFlow
        )
    }
}

data class MainUiModel(
    val launchLogin: Event<LoginBuilder>? = null,
    val continueWithFlow: Event<NullModel>? = null
)