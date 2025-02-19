package com.`fun`.pokemon.presentation.favorite.model

sealed interface FavoriteEvent {
    data class OnError(val msg: String) : FavoriteEvent
    data object GoBack: FavoriteEvent
    data class ToDetail(val name: String): FavoriteEvent
}