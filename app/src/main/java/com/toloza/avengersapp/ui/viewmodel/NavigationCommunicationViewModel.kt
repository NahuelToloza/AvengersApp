package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toloza.avengersapp.util.Event

class NavigationCommunicationViewModel : ViewModel() {
    private val _uiState = MutableLiveData<NavigationCommunicationUiModel>()
    val uiState: LiveData<NavigationCommunicationUiModel>
        get() = _uiState

    fun changeToolbarTitle(title: String) {
        emitUiModel(changeTitle = Event(title))
    }

    fun showNavigation(shouldShowNavigation: Boolean) {
        emitUiModel(showNavigationBar = Event(shouldShowNavigation))
    }

    private fun emitUiModel(
        changeTitle: Event<String>? = null,
        showNavigationBar: Event<Boolean>? = null
    ) {
        _uiState.value = NavigationCommunicationUiModel(
            changeTitle = changeTitle,
            showNavigationBar = showNavigationBar
        )
    }
}

data class NavigationCommunicationUiModel(
    val changeTitle: Event<String>? = null,
    val showNavigationBar: Event<Boolean>? = null
)