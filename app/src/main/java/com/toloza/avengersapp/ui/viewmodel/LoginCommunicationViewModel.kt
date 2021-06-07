package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.util.Event

class LoginCommunicationViewModel : ViewModel() {
    private val _uiModel = MutableLiveData<LoginCommunicationUiModel>()
    val uiModel: LiveData<LoginCommunicationUiModel>
        get() = _uiModel

    fun notifyLoginSuccess() {
        _uiModel.value = LoginCommunicationUiModel(notifyLoginSuccess = Event(NullModel()))
    }
}

data class LoginCommunicationUiModel(
    val notifyLoginSuccess: Event<NullModel>? = null
)