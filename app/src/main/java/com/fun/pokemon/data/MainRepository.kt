package com.`fun`.pokemon.data

import com.`fun`.pokemon.api.service.Service
import com.`fun`.pokemon.data.model.PokemonData
import com.`fun`.pokemon.data.model.toData
import com.`fun`.pokemon.storage.dao.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject internal constructor(
    private val service: Service,
    private val dao: FavoriteDao,
) {
    fun getPokemonList(): Flow<Result<List<PokemonData>>> = flow {
        emit(
            runCatching {
                service.getPokemonList().results.map {
                    it.toData()
                }
            })
    }.flowOn(Dispatchers.IO)

    fun getFavoritePokemonList() = dao.getFavorites().map {
        it.map { favorite ->
            favorite.toData()
        }
    }
}
