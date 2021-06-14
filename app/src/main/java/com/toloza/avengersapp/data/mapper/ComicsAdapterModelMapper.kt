package com.toloza.avengersapp.data.mapper

import com.toloza.avengersapp.data.model.internal.CharacterAdapterModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.CharacterDetailHeaderModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.ComicsAdapterModel

class ComicsAdapterModelMapper {
    fun toComicsAdapterModel(character: CharacterAdapterModel): ComicsAdapterModel {
        val headerModel = CharacterDetailHeaderModel(
            character.description,
            character.imageUrl
        )
        return ComicsAdapterModel(
            headerModel = headerModel,
            comics = character.comics
        )
    }
}
