package com.`fun`.pokemon.navigation

enum class Screen {
  MAIN,
  DETAIL,
  FAVORITE,
}

sealed class NavigationItem(val route: String) {
  data object Main : NavigationItem(Screen.MAIN.name)
  data object Detail : NavigationItem(Screen.DETAIL.name)
  data object Favorite : NavigationItem(Screen.FAVORITE.name)
}
