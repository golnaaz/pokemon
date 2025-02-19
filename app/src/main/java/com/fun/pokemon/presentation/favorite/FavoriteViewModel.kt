package com.`fun`.pokemon.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`fun`.pokemon.data.MainRepository
import com.`fun`.pokemon.presentation.favorite.model.FavoriteAction
import com.`fun`.pokemon.presentation.favorite.model.FavoriteEvent
import com.`fun`.pokemon.presentation.favorite.model.FavoriteViewState
import com.`fun`.pokemon.presentation.main.model.MainAction
import com.`fun`.pokemon.presentation.main.model.MainEvent
import com.`fun`.pokemon.presentation.main.model.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _events = Channel<FavoriteEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(FavoriteViewState())
    val state: StateFlow<FavoriteViewState> = _state.asStateFlow()

    init {
        fetchPokemonList()
    }

    fun processAction(action: FavoriteAction) {
        when (action) {
            FavoriteAction.GoBack -> _events.trySend(FavoriteEvent.GoBack)
            is FavoriteAction.OnItemClick -> _events.trySend(FavoriteEvent.ToDetail(action.name))
        }
    }

    private fun fetchPokemonList() {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            repository.getFavoritePokemonList().collectLatest { result ->
                _state.update {
                    it.copy(
                        items = result,
                        isLoading = false
                    )
                }

            }
        }
    }
}