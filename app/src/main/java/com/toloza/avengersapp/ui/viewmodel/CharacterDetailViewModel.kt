package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.data.model.internal.CharacterAdapterModel
import com.toloza.avengersapp.data.model.internal.ComicModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.CharacterDetailHeaderModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.ComicsAdapterModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.CharacterDetailUiModel
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableLiveData<CharacterDetailUiModel>()
    val uiState: LiveData<CharacterDetailUiModel>
        get() = _uiState

    fun setUpDetail(
        character: CharacterAdapterModel?
    ) = viewModelScope.launch(dispatcherProvider.computation) {
        character?.let { character ->
            //TODO PEDIR COMICS
            //TODO BORRAR MOCK
            val mock = listOf(
                ComicModel("Pepe", "1999"),
                ComicModel("Pepe", "1999"),
                ComicModel("Pepe", "1999"),
                ComicModel("Pepe", "1999")
            )
            val model2 = CharacterDetailHeaderModel(
                character.description,
                character.imageUrl
            )
            val model = ComicsAdapterModel(
                headerModel = model2,
                comics = mock
            )
            emitUiModel(showInfoDetail = Event(model))
        }
    }

    private suspend fun emitUiModel(
        showError: Event<ServerError>? = null,
        showLoading: Boolean = false,
        showInfoDetail: Event<ComicsAdapterModel>? = null
    ) = withContext(dispatcherProvider.main) {
        _uiState.value = CharacterDetailUiModel(
            showError = showError,
            showLoading = showLoading,
            showInfoDetail = showInfoDetail
        )
    }
}