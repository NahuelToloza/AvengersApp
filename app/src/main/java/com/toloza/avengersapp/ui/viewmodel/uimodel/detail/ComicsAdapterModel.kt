package com.toloza.avengersapp.ui.viewmodel.uimodel.detail

import com.toloza.avengersapp.data.model.internal.ComicModel

data class ComicsAdapterModel(
    val headerModel: CharacterDetailHeaderModel? = null,
    val comics: List<ComicModel>
)

data class CharacterDetailHeaderModel(
    val description: String,
    val imageUrl: String,
)