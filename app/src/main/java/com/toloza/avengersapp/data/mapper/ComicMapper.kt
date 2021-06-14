package com.toloza.avengersapp.data.mapper

import com.toloza.avengersapp.data.model.internal.ComicModel
import com.toloza.avengersapp.extensions.findBetweenParenthesis
import com.toloza.avengersapp.service.dto.Comic

class ComicMapper {
    fun toComicModel(comics: Comic): List<ComicModel> {
        return comics.items.map { comic ->
            ComicModel(
                name = comic.name,
                year = comic.name.findBetweenParenthesis()
            )
        }
    }
}
