package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.mapper.EventMapper
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.service.repository.AvengersRepository
import com.toloza.avengersapp.data.model.internal.AvengersEventAdapterModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.events.EventsUiModel
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel(
    private val repository: AvengersRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val eventMapper: EventMapper
) : ViewModel() {
    private val _uiState = MutableLiveData<EventsUiModel>()
    val uiState: LiveData<EventsUiModel>
        get() = _uiState

    private var eventsList = mutableListOf<AvengersEventAdapterModel>()

    fun getEvents(page: Int = 0) = viewModelScope.launch(dispatcherProvider.computation) {
        showLoading()

        when (val result = repository.getEvents(page)) {
            is Result.Success -> {
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

    fun updateItem(adapterPosition: Int): List<AvengersEventAdapterModel> {
        val newList = eventsList.toList()
        newList[adapterPosition].isExpanded = !newList[adapterPosition].isExpanded
        return newList
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
