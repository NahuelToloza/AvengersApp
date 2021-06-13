package com.toloza.avengersapp.ui.adapter.model

import com.toloza.avengersapp.data.model.AvengersEvent
import com.toloza.avengersapp.data.model.Comic

data class AvengersEventAdapterModel(
    val event: AvengersEvent,
    val isExpanded: Boolean = false,
    val comicList: List<Comic>
)