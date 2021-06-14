package com.toloza.avengersapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.Result
import com.toloza.avengersapp.data.mapper.EventMapper
import com.toloza.avengersapp.extensions.getGenericError
import com.toloza.avengersapp.service.dto.*
import com.toloza.avengersapp.service.repository.AvengersRepository
import com.toloza.avengersapp.ui.viewmodel.CharactersUiModel
import com.toloza.avengersapp.ui.viewmodel.EventsViewModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.events.EventsUiModel
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
class EventsViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: EventsViewModel

    private lateinit var repository: AvengersRepository
    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider
    private lateinit var eventMapper: EventMapper

    @Before
    fun setUp() {
        repository = mock()
        dispatcherProvider = provideFakeCoroutinesDispatcherProvider()
        eventMapper = mock()

        viewModel = EventsViewModel(
            repository = repository,
            dispatcherProvider = dispatcherProvider,
            eventMapper = eventMapper
        )
    }

    @Test
    fun `get characters api success, page 0`() = runBlockingTest {
        val fakeEventsDto = EventsDto(DataEvent(listOf(
            Event(
            id = 74535,
            title = "Panchos gratis",
            description = "Panchos gratis en el obelisco",
            comics = Comic(listOf(ComicItem("El comic del pancho"))),
            thumbnail = Thumbnail("www.nose.com", ".png")
        )
        )))

        whenever(repository.getEvents()).thenReturn(Result.Success(fakeEventsDto))
        viewModel.getEvents()

        val result = viewModel.uiState.getOrAwaitValue()

        val adapterModelList = eventMapper.toEventAdapterModel(fakeEventsDto)
        val expected = EventsUiModel(showEventsList = Event(adapterModelList))

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `get characters api success, page different to 0`() = runBlockingTest {
        val fakeEventsDto = EventsDto(DataEvent(listOf(
            Event(
                id = 74535,
                title = "Panchos gratis",
                description = "Panchos gratis en el obelisco",
                comics = Comic(listOf(ComicItem("El comic del pancho"))),
                thumbnail = Thumbnail("www.nose.com", ".png")
            )
        )))
        val page = 2

        whenever(repository.getEvents(page)).thenReturn(Result.Success(fakeEventsDto))
        viewModel.getEvents(page)

        val result = viewModel.uiState.getOrAwaitValue()

        val adapterModelList = eventMapper.toEventAdapterModel(fakeEventsDto)
        val expected = EventsUiModel(updateEventsList = Event(adapterModelList))

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `get characters api error`() = runBlockingTest {
        val fakeError = getGenericError("Algun error muy feo")

        whenever(repository.getEvents()).thenReturn(Result.Error(fakeError))
        viewModel.getEvents()

        val result = viewModel.uiState.getOrAwaitValue()
        val expected = EventsUiModel(showError = Event(fakeError))

        Assert.assertEquals(expected, result)
    }
}