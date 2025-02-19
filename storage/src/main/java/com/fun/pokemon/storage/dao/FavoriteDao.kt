package com.`fun`.pokemon.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.`fun`.pokemon.storage.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(venue: FavoriteEntity)

    @Delete
    suspend fun removeFavorite(venue: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE name = :name LIMIT 1")
    suspend fun getPokemonByName(name: String): FavoriteEntity?
}