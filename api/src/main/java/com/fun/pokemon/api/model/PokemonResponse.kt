package com.`fun`.pokemon.api.model

data class PokemonResponse(
    val results: List<Pokemon>,
)

data class Pokemon(
    val name: String,
    val url: String,
)