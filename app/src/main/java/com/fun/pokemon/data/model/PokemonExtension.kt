package com.`fun`.pokemon.data.model

import com.`fun`.pokemon.api.model.Pokemon
import com.`fun`.pokemon.api.model.PokemonInfoResponse
import com.`fun`.pokemon.storage.model.FavoriteEntity

fun Pokemon.toData(): PokemonData {
    return PokemonData(
        name = name,
        url = url,
        imageUrl = getImageUrl(url.split("/".toRegex()).dropLast(1).last())
    )
}

private fun getImageUrl(index: String): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
            "pokemon/other/official-artwork/$index.png"
}

fun PokemonInfoData.toEntity() = FavoriteEntity(
    id = id,
    isFavorite = isFavorite,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
)

fun FavoriteEntity.toData() = PokemonInfoData(
    id = id,
    isFavorite = isFavorite,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
)

fun PokemonInfoResponse.toData() = PokemonInfoData(
    id = id,
    isFavorite = false,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
)

