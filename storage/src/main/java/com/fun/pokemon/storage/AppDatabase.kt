package com.`fun`.pokemon.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.`fun`.pokemon.storage.dao.FavoriteDao
import com.`fun`.pokemon.storage.model.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): FavoriteDao
}