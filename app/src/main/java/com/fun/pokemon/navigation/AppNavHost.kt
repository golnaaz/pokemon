package com.`fun`.pokemon.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.`fun`.pokemon.presentation.detail.DetailScreen
import com.`fun`.pokemon.presentation.favorite.FavoriteScreen
import com.`fun`.pokemon.presentation.main.MainScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(NavigationItem.Main.route) {
            MainScreen(navController)
        }
        composable(NavigationItem.Detail.route) {
            DetailScreen(navController)
        }
        composable(NavigationItem.Favorite.route) {
            FavoriteScreen(navController)
        }
    }
}
