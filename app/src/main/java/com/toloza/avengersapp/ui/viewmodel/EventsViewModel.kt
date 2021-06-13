package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.model.Character
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.service.repository.AvengersRepository
import com.toloza.avengersapp.ui.adapter.model.AvengersEventAdapterModel
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel(
    private val repository: AvengersRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<EventsUiModel>()
    val uiState: LiveData<EventsUiModel>
        get() = _uiState

    private var eventsList = mutableListOf<AvengersEventAdapterModel>()

    fun getEvents(page: Int = 0) = viewModelScope.launch(dispatcherProvider.computation) {
        showLoading()

        when (val result = repository.getEvents(page)) {
            is Result.Success -> {
                //TODO OJO QUE VA OTRA LISTA ADENTRO
                val list = eventMapper.toEventAdapterModel(result.data)
                eventsList.addAll(list)

                if (page == 0) {
                    emitUiModel(showEventsList = Event(eventsList.toList()))
                } else {
                    emitUiModel(updateEventsList = Event(eventsList.toList()))
                }
            }
            is Result.Error -> {
                emitUiModel(showError = Event(result.error))
            }
        }
    }

    private suspend fun showLoading() {
        emitUiModel(showLoading = true)
    }

    private suspend fun emitUiModel(
        showError: Event<ServerError>? = null,
        showLoading: Boolean = false,
        showEventsList: Event<List<AvengersEventAdapterModel>>? = null,
        updateEventsList: Event<List<AvengersEventAdapterModel>>? = null,
    ) = withContext(dispatcherProvider.main) {
        _uiState.value = EventsUiModel(
            showError = showError,
            showLoading = showLoading,
            showEventsList = showEventsList,
            updateEventsList = updateEventsList
        )
    }
}

data class EventsUiModel(
    val showError: Event<ServerError>? = null,
    val showLoading: Boolean = false,
    val showEventsList: Event<List<AvengersEventAdapterModel>>? = null,
    val updateEventsList: Event<List<AvengersEventAdapterModel>>? = null,
)
