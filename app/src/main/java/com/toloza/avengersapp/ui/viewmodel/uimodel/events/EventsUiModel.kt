package com.toloza.avengersapp.ui.viewmodel.uimodel.events

import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.data.model.internal.AvengersEventAdapterModel
import com.toloza.avengersapp.util.Event

data class EventsUiModel(
    val showError: Event<ServerError>? = null,
    val showLoading: Boolean = false,
    val showEventsList: Event<List<AvengersEventAdapterModel>>? = null,
    val updateEventsList: Event<List<AvengersEventAdapterModel>>? = null,
)