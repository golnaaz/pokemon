package com.`fun`.pokemon.presentation.main.model


sealed interface MainEvent {
    data class OnError(val msg: String) : MainEvent
    data class ToDetail(val name: String) : MainEvent
    data object ToFavorite : MainEvent
}