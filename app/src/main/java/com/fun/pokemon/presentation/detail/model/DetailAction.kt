package com.`fun`.pokemon.presentation.detail.model

sealed interface DetailAction {
    data class Init(val name: String) : DetailAction
    data object GoBack : DetailAction
    data class ToggleFavorite(val isFavorite: Boolean) : DetailAction
}