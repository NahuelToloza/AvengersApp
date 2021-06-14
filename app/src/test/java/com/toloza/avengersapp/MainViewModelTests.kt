package com.toloza.avengersapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.ui.view.model.HomeNavigationTab
import com.toloza.avengersapp.ui.viewmodel.MainViewModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.main.MainUiModel
import com.toloza.avengersapp.util.Event
import com.toloza.avengersapp.util.getOrAwaitValue
import com.toloza.avengersapp.util.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = provideFakeCoroutinesDispatcherProvider()

        viewModel = MainViewModel(
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Test
    fun `tab clicked character`(){
        viewModel.onTabItemClicked(HomeNavigationTab.CHARACTERS)

        val result = viewModel.uiState.getOrAwaitValue()
        val expected = MainUiModel(showCharactersScreen = Event(NullModel()))

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `tab clicked event`(){
        viewModel.onTabItemClicked(HomeNavigationTab.EVENTS)

        val result = viewModel.uiState.getOrAwaitValue()
        val expected = MainUiModel(showEventsScreen = Event(NullModel()))

        Assert.assertEquals(expected, result)
    }
}