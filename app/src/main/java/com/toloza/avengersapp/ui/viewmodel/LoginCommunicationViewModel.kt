package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toloza.avengersapp.ui.view.HomeNavigationModel
import com.toloza.avengersapp.util.Event

class LoginCommunicationViewModel : ViewModel() {
    private val _uiModel = MutableLiveData<LoginCommunicationUiModel>()
    val uiModel: LiveData<LoginCommunicationUiModel>
        get() = _uiModel

    fun notifyLoginSuccess() {
        val tabList = HomeNavigationModel.getTabList()
        _uiModel.value = LoginCommunicationUiModel(notifyLoginSuccess = Event(tabList))
    }
}

data class LoginCommunicationUiModel(
    val notifyLoginSuccess: Event<List<HomeNavigationModel>>? = null
)