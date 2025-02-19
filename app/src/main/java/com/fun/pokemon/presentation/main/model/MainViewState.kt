package com.`fun`.pokemon.presentation.main.model

import com.`fun`.pokemon.data.model.PokemonData

data class MainViewState(
    val items: List<PokemonData> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
)