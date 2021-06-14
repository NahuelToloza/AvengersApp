package com.toloza.avengersapp.data.mapper

import com.toloza.avengersapp.data.model.internal.AvengersEvent
import com.toloza.avengersapp.data.model.internal.AvengersEventAdapterModel
import com.toloza.avengersapp.data.model.internal.ComicModel
import com.toloza.avengersapp.extensions.findBetweenParenthesis
import com.toloza.avengersapp.extensions.replaceHttpForHttps
import com.toloza.avengersapp.service.dto.EventsDto
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.ComicsAdapterModel

class EventMapper(
    private val comicMapper: ComicMapper
) {
    fun toEventAdapterModel(eventDao: EventsDto): List<AvengersEventAdapterModel> {
        return eventDao.data.results.map {
            val path = it.thumbnail.path.replaceHttpForHttps()

            val event = AvengersEvent(
                id = it.id,
                imageUrl = "$path.${it.thumbnail.extension}",
                title = it.title,
                description = it.description
            )

            val comics = comicMapper.toComicModel(it.comics)
            AvengersEventAdapterModel(
                event = event,
                comicsAdapterModel = ComicsAdapterModel(comics = comics)
            )
        }
    }
}