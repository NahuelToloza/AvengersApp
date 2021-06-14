package com.toloza.avengersapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.mapper.CharacterMapper
import com.toloza.avengersapp.extensions.getGenericError
import com.toloza.avengersapp.service.dto.*
import com.toloza.avengersapp.service.repository.AvengersRepository
import com.toloza.avengersapp.ui.viewmodel.CharactersUiModel
import com.toloza.avengersapp.ui.viewmodel.CharactersViewModel
import com.toloza.avengersapp.util.Event
import com.toloza.avengersapp.util.getOrAwaitValue
import com.toloza.avengersapp.util.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel

    private lateinit var repository: AvengersRepository
    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider
    private lateinit var characterMapper: CharacterMapper

    @Before
    fun setUp() {
        repository = mock()
        dispatcherProvider = provideFakeCoroutinesDispatcherProvider()
        characterMapper = mock()

        viewModel = CharactersViewModel(
            repository = repository,
            dispatcherProvider = dispatcherProvider,
            characterMapper = characterMapper
        )
    }

    @Test
    fun `get characters api success, page 0`() = runBlockingTest {
        val fakeCharactersDto = CharactersDto(Data(listOf(Character(
            id = 10899,
            name = "Super maravilla",
            description = "El mejor superheroe del planeta marte",
            thumbnail = Thumbnail("www.algo.com", ".jpg"),
            comics = Comic(listOf())
        ))))

        whenever(repository.getCharacters()).thenReturn(Result.Success(fakeCharactersDto))
        viewModel.getCharacters()

        val result = viewModel.uiState.getOrAwaitValue()

        val adapterModelList = characterMapper.toCharacterAdapterModel(fakeCharactersDto)
        val expected = CharactersUiModel(showCharactersList = Event(adapterModelList))

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `get characters api success, page different to 0`() = runBlockingTest {
        val fakeCharactersDto = CharactersDto(Data(listOf(Character(
            id = 10899,
            name = "Super maravilla",
            description = "El mejor superheroe del planeta marte",
            thumbnail = Thumbnail("www.algo.com", ".jpg"),
            comics = Comic(listOf())
        ))))
        val page = 2

        whenever(repository.getCharacters(page)).thenReturn(Result.Success(fakeCharactersDto))
        viewModel.getCharacters(page)

        val result = viewModel.uiState.getOrAwaitValue()

        val adapterModelList = characterMapper.toCharacterAdapterModel(fakeCharactersDto)
        val expected = CharactersUiModel(updateCharactersList = Event(adapterModelList))

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `get characters api error`() = runBlockingTest {
        val fakeError = getGenericError("Algun error muy feo")

        whenever(repository.getCharacters()).thenReturn(Result.Error(fakeError))
        viewModel.getCharacters()

        val result = viewModel.uiState.getOrAwaitValue()
        val expected = CharactersUiModel(showError = Event(fakeError))

        Assert.assertEquals(expected, result)
    }
}