package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.ui.view.model.HomeNavigationTab
import com.toloza.avengersapp.ui.viewmodel.uimodel.main.MainUiModel
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableLiveData<MainUiModel>()
    val uiState: LiveData<MainUiModel>
        get() = _uiState

    fun onTabItemClicked(itemSelected: HomeNavigationTab) =
        viewModelScope.launch(dispatcherProvider.computation) {
            when (itemSelected) {
                HomeNavigationTab.CHARACTERS -> {
                    emitUiModel(showCharactersScreen = Event(NullModel()))
                }
                HomeNavigationTab.EVENTS -> {
                    emitUiModel(showEventsScreen = Event(NullModel()))
                }
            }
        }

    private suspend fun emitUiModel(
        showCharactersScreen: Event<NullModel>? = null,
        showEventsScreen: Event<NullModel>? = null
    ) = withContext(dispatcherProvider.main) {
        _uiState.value = MainUiModel(
            showCharactersScreen = showCharactersScreen,
            showEventsScreen = showEventsScreen
        )
    }
}
