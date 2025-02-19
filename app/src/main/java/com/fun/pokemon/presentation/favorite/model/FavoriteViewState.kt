package com.`fun`.pokemon.presentation.favorite.model

import com.`fun`.pokemon.data.model.PokemonInfoData

data class FavoriteViewState (
    val items: List<PokemonInfoData> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
)