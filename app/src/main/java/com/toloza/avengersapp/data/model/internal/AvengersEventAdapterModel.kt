package com.toloza.avengersapp.data.model.internal

import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.ComicsAdapterModel

data class AvengersEventAdapterModel(
    val event: AvengersEvent,
    var isExpanded: Boolean = false,
    val comicsAdapterModel: ComicsAdapterModel
)