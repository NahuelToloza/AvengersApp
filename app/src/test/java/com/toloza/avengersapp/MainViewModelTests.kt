package com.toloza.avengersapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.toloza.avengersapp.data.CoroutinesDispatcherProvider
import com.toloza.avengersapp.data.login.LoginManager
import com.toloza.avengersapp.data.model.User
import com.toloza.avengersapp.data.model.core.NullModel
import com.toloza.avengersapp.service.repository.LoginRepository
import com.toloza.avengersapp.ui.viewmodel.MainUiModel
import com.toloza.avengersapp.ui.viewmodel.MainViewModel
import com.toloza.avengersapp.util.Event
import com.toloza.avengersapp.util.test.getOrAwaitValue
import com.toloza.avengersapp.util.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private lateinit var repository: LoginRepository
    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider
    private lateinit var loginManager: LoginManager

    @Before
    fun setUp() {
        repository = mock()
        dispatcherProvider = provideFakeCoroutinesDispatcherProvider()
        loginManager = mock()

        viewModel = MainViewModel(
            repository = repository,
            dispatcherProvider = dispatcherProvider,
            loginManager = loginManager
        )
    }

    @Test
    fun `test setup user information NOT null`() = runBlockingTest(dispatcherProvider.computation) {
        val fakeUser = User("pepe@pepe.com", "Pepe Perez")
        whenever(repository.getLocalUser()).thenReturn(fakeUser)

        viewModel.setUpUserInformation()
        val result = viewModel.uiModel.getOrAwaitValue()
        val expected = MainUiModel(continueWithFlow = Event(NullModel()))

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `test setup user information null`() = runBlockingTest(dispatcherProvider.computation) {
        val fakeUser = null
        whenever(repository.getLocalUser()).thenReturn(fakeUser)

        viewModel.setUpUserInformation()
        val result = viewModel.uiModel.getOrAwaitValue()

        Assert.assertNotNull(result.launchLogin)
    }
}