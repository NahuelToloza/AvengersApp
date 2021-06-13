package com.toloza.avengersapp.data

import com.toloza.avengersapp.extensions.replaceHttpForHttps
import com.toloza.avengersapp.service.dto.CharactersDto
import com.toloza.avengersapp.data.model.Character

class CharacterMapper {
    fun toCharacterAdapterModel(charactersDto: CharactersDto): List<Character> {
        return charactersDto.data.results.map {
            val path = it.thumbnail.path.replaceHttpForHttps()
            Character(
                id = it.id,
                imageUrl = "$path.${it.thumbnail.extension}",
                title = it.name,
                description = it.description
            )
        }
    }
}
