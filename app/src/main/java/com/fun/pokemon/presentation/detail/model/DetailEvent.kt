package com.`fun`.pokemon.presentation.detail.model


sealed interface DetailEvent {
    data class OnError(val msg: String) : DetailEvent
    data object GoBack : DetailEvent
}