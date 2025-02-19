package com.`fun`.pokemon.presentation.main

import app.cash.turbine.test
import com.`fun`.pokemon.data.MainRepository
import com.`fun`.pokemon.data.model.PokemonData
import com.`fun`.pokemon.presentation.MainDispatcherRule
import com.`fun`.pokemon.presentation.main.model.MainAction
import com.`fun`.pokemon.presentation.main.model.MainEvent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelShould {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: MainRepository = mockk(relaxed = true)
    private lateinit var viewModel: MainViewModel
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        val failureFlow = flowOf(Result.failure<List<PokemonData>>(Throwable(errorMessage)))
        every { repository.getPokemonList() } returns failureFlow

        val successFlow = flowOf(Result.success(mockPokemonList))
        coEvery { repository.getPokemonList() } returns successFlow


        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenFetchPokemonList_GivenSuccess_ThenUpdatesState() {
        runTest {
            viewModel.state.test {
                val finalState = awaitItem()
                assertThat(finalState.isLoading).isFalse()
                assertThat(finalState.items).isEqualTo(mockPokemonList)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun whenFetchPokemonList_GivenFailure_ThenError() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isTrue()

            val finalState = awaitItem()
            assertThat(finalState.isLoading).isFalse()

            cancelAndIgnoreRemainingEvents()
        }

        viewModel.events.test {
            assertThat(expectMostRecentItem()).isEqualTo(MainEvent.OnError(errorMessage))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenItemClicked_shouldNavigateToDetail() {
        runTest {
            viewModel.processAction(
                MainAction.OnItemClick("Pikachu")
            )
            viewModel.events.test {
                assertThat(expectMostRecentItem()).isEqualTo(MainEvent.ToDetail("Pikachu"))
            }
        }
    }

    @Test
    fun whenFavoriteClicked_shouldNavigateToFavorites() {
        runTest {
            viewModel.processAction(
                MainAction.OnFavClick
            )
            viewModel.events.test {
                assertThat(expectMostRecentItem()).isEqualTo(MainEvent.ToFavorite)
            }
        }
    }
}

val errorMessage = "Network Error"

val mockPokemonList =
    listOf(
        PokemonData("Pikachu", "", ""),
        PokemonData("Charmander", "", "")
    )
