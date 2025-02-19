package com.`fun`.pokemon.presentation.detail

import app.cash.turbine.test
import com.`fun`.pokemon.data.DetailRepository
import com.`fun`.pokemon.data.model.PokemonInfoData
import com.`fun`.pokemon.presentation.MainDispatcherRule
import com.`fun`.pokemon.presentation.detail.model.DetailAction
import com.`fun`.pokemon.presentation.detail.model.DetailEvent
import com.`fun`.pokemon.presentation.main.model.MainAction
import com.`fun`.pokemon.presentation.main.model.MainEvent
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelShould {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: DetailRepository = mockk(relaxed = true)
    private lateinit var viewModel: DetailViewModel
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = DetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenFetchPokemon_WhenSuccess_ThenUpdatesState() = runTest {

        val successFlow = Result.success(mockPokemon)
        coEvery { repository.getPokemon("Pikachu") } returns successFlow

        viewModel.processAction(DetailAction.Init("Pikachu"))

        viewModel.state.test {
            val finalState = awaitItem()
            assertThat(finalState.isLoading).isFalse()
            assertThat(finalState.item).isEqualTo(mockPokemon)
            assertThat(finalState.isFavorite).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun givenFetchPokemon_WhenFail_ThenUpdatesState() = runTest {

        val errorMessage = "Pokemon not found"
        val failureFlow = Result.failure<PokemonInfoData>(Throwable(errorMessage))
        coEvery { repository.getPokemon("Pikachu") } returns failureFlow

        viewModel.processAction(DetailAction.Init("Pikachu"))

        viewModel.state.test {
            assertThat(awaitItem().isLoading).isTrue()

            val finalState = awaitItem()
            assertThat(finalState.isLoading).isFalse()
            assertThat(finalState.item).isNull()

            cancelAndIgnoreRemainingEvents()
        }

        viewModel.events.test {
            val errorEvent = expectMostRecentItem() as DetailEvent.OnError
            assertThat(errorEvent.msg).isEqualTo(errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenGoBackClicked_shouldNavigateToMain() {
        runTest {
            viewModel.processAction(
                DetailAction.GoBack
            )
            viewModel.events.test {
                assertThat(expectMostRecentItem()).isEqualTo(DetailEvent.GoBack)
            }
        }
    }

    val mockPokemon = PokemonInfoData(name = "Pikachu", isFavorite = true)
}