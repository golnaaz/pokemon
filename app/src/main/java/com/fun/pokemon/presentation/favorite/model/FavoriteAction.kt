package com.`fun`.pokemon.presentation.favorite.model

sealed interface FavoriteAction {
    data object GoBack: FavoriteAction
    data class OnItemClick(val name:String): FavoriteAction
}