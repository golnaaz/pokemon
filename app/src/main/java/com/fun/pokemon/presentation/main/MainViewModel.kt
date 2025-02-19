package com.`fun`.pokemon.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`fun`.pokemon.data.MainRepository
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
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _events = Channel<MainEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    init {
        fetchPokemonList()
    }

    fun processAction(action: MainAction) {
        when (action) {
            is MainAction.OnItemClick -> {
                _events.trySend(MainEvent.ToDetail(action.name))
            }

            MainAction.OnFavClick -> _events.trySend(MainEvent.ToFavorite)
        }
    }

    private fun fetchPokemonList() {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            repository.getPokemonList().collectLatest { response ->
                response.onSuccess { result ->
                    _state.update {
                        it.copy(
                            items = result,
                            isLoading = false
                        )
                    }
                }.onFailure { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _events.trySend(MainEvent.OnError(result.message ?: "Error"))
                }

            }
        }
    }
}