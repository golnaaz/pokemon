package com.`fun`.pokemon.data.model

data class PokemonInfoData(
    val id: Int = 0,
    val name: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val experience: Int = 0,
    val isFavorite: Boolean = false,
) {
    companion object {
        val EMPTY = PokemonInfoData()
    }
}