package com.toloza.avengersapp.ui.viewmodel.uimodel.detail

import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.util.Event

data class CharacterDetailUiModel(
    val showError: Event<ServerError>? = null,
    val showLoading: Boolean = false,
    val showInfoDetail: Event<ComicsAdapterModel>? = null
)