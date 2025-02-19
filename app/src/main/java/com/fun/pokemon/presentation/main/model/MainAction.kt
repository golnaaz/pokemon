package com.`fun`.pokemon.presentation.main.model

sealed interface MainAction {
    data class OnItemClick(val name: String) : MainAction
    data object OnFavClick : MainAction
}