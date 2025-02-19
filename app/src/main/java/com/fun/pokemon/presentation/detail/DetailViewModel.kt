package com.`fun`.pokemon.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`fun`.pokemon.data.DetailRepository
import com.`fun`.pokemon.presentation.detail.model.DetailAction
import com.`fun`.pokemon.presentation.detail.model.DetailEvent
import com.`fun`.pokemon.presentation.detail.model.DetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
) : ViewModel() {

    private val _events = Channel<DetailEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(DetailViewState())
    val state: StateFlow<DetailViewState> = _state.asStateFlow()

    fun processAction(action: DetailAction) {
        when (action) {
            is DetailAction.Init -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        name = action.name,
                    )
                }
                fetchPokemon(action.name)
            }

            is DetailAction.ToggleFavorite -> {
                _state.update {
                    it.copy(
                        isFavorite = !action.isFavorite
                    )
                }
                viewModelScope.launch {
                    repository.toggleFavorite(
                        state.value.item.copy(
                            isFavorite = state.value.isFavorite
                        )
                    )
                }
            }

            DetailAction.GoBack -> {
                _events.trySend(DetailEvent.GoBack)
            }
        }
    }

    private fun fetchPokemon(name: String) {
        viewModelScope.launch {
            repository.getPokemon(name)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            item = result,
                            isFavorite = result.isFavorite
                        )
                    }
                }.onFailure { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _events.trySend(DetailEvent.OnError(result.message ?: "Error"))
                }

        }
    }
}