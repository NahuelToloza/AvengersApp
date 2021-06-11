package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toloza.avengersapp.ui.view.model.HomeNavigationTab
import com.toloza.avengersapp.util.Event

class LoginCommunicationViewModel : ViewModel() {
    private val _uiState = MutableLiveData<LoginCommunicationUiModel>()
    val uiState: LiveData<LoginCommunicationUiModel>
        get() = _uiState

    fun notifyLoginSuccess() {
        val tabList = HomeNavigationTab.getTabList()
        _uiState.value = LoginCommunicationUiModel(notifyLoginSuccess = Event(tabList))
    }
}

data class LoginCommunicationUiModel(
    val notifyLoginSuccess: Event<List<HomeNavigationTab>>? = null
)