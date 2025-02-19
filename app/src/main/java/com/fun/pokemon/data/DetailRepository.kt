package com.`fun`.pokemon.data

import com.`fun`.pokemon.api.service.Service
import com.`fun`.pokemon.data.model.PokemonInfoData
import com.`fun`.pokemon.data.model.toData
import com.`fun`.pokemon.data.model.toEntity
import com.`fun`.pokemon.storage.dao.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepository @Inject internal constructor(
    private val dao: FavoriteDao,
    private val service: Service,
) {
    suspend fun getPokemon(name: String): Result<PokemonInfoData> {
        return withContext(Dispatchers.IO) {
            try {
                val localPokemon = dao.getPokemonByName(name)
                if (localPokemon != null) {
                    return@withContext Result.success(localPokemon.toData())
                }
                return@withContext Result.success(service.getPokemonInfo(name).toData())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }

        }
    }

    suspend fun toggleFavorite(pokemon: PokemonInfoData) {
        if (!pokemon.isFavorite) {
            dao.removeFavorite(pokemon.toEntity())
        } else {
            dao.addFavorite(pokemon.toEntity())
        }
    }
}