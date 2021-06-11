package com.toloza.avengersapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toloza.avengersapp.data.CharacterMapper
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.service.repository.AvengersRepository
import com.toloza.avengersapp.ui.adapter.model.Character
import com.toloza.avengersapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(
    private val repository: AvengersRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val characterMapper: CharacterMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<CharactersUiModel>()
    val uiState: LiveData<CharactersUiModel>
        get() = _uiState

    private var characterList = mutableListOf<Character>()

    fun getCharacters(page: Int = 0) = viewModelScope.launch(dispatcherProvider.computation) {
        showLoading()

        when (val result = repository.getCharacters(page)) {
            is Result.Success -> {
                val list = characterMapper.toCharacterAdapterModel(result.data)
                characterList.addAll(list)

                if (page == 0) {
                    emitUiModel(showCharactersList = Event(characterList.toList()))
                } else {
                    emitUiModel(updateCharactersList = Event(characterList.toList()))
                }
            }
            is Result.Error -> {
                emitUiModel(showError = Event(result.error))
            }
        }
    }

    private suspend fun showLoading() {
        emitUiModel(showLoading = true)
    }

    private suspend fun emitUiModel(
        showCharactersList: Event<List<Character>>? = null,
        updateCharactersList: Event<List<Character>>? = null,
        showError: Event<ServerError>? = null,
        showLoading: Boolean = false
    ) = withContext(dispatcherProvider.main) {
        _uiState.value = CharactersUiModel(
            showCharactersList = showCharactersList,
            updateCharactersList = updateCharactersList,
            showError = showError,
            showLoading = showLoading
        )
    }
}

data class CharactersUiModel(
    val showCharactersList: Event<List<Character>>? = null,
    val updateCharactersList: Event<List<Character>>? = null,
    val showError: Event<ServerError>? = null,
    val showLoading: Boolean = false
)
