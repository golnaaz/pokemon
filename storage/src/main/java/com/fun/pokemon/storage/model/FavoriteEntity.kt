package com.`fun`.pokemon.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    val id: Int,
    val isFavorite: Boolean,
    @PrimaryKey
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
)