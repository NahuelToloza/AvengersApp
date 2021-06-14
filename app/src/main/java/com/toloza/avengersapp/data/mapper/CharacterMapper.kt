package com.toloza.avengersapp.data.mapper

import com.toloza.avengersapp.extensions.replaceHttpForHttps
import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.data.model.internal.CharacterAdapterModel

class CharacterMapper {
    fun toCharacterAdapterModel(charactersDto: CharactersDto): List<CharacterAdapterModel> {
        return charactersDto.data.results.map {
            val path = it.thumbnail.path.replaceHttpForHttps()
            CharacterAdapterModel(
                id = it.id,
                imageUrl = "$path.${it.thumbnail.extension}",
                title = it.name,
                description = it.description
            )
        }
    }
}
