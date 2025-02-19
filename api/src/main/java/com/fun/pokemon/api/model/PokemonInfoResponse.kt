package com.`fun`.pokemon.api.model

data class PokemonInfoResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
    val sprites: Sprites
)

data class Sprites(
    val back_default: String,
    val front_default: String,
)

