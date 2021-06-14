package com.toloza.avengersapp.ui.viewmodel.uimodel.main

import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.util.Event

data class MainUiModel(
    val showCharactersScreen: Event<NullModel>? = null,
    val showEventsScreen: Event<NullModel>? = null
)