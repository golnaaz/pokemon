package com.`fun`.pokemon.presentation.detail.model

import com.`fun`.pokemon.data.model.PokemonInfoData

data class DetailViewState(
    val item: PokemonInfoData = PokemonInfoData.EMPTY,
    val name: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
)
