package com.`fun`.pokemon.presentation.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.`fun`.pokemon.navigation.NavigationItem
import com.`fun`.pokemon.presentation.detail.model.DetailContentArgs
import com.`fun`.pokemon.presentation.main.model.MainEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun MainScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by remember { viewModel.state }.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.onEach {
            when (it) {
                is MainEvent.OnError -> Toast.makeText(
                    context,
                    it.msg,
                    Toast.LENGTH_SHORT
                ).show()

                is MainEvent.ToDetail -> {
                    DetailContentArgs.pokemonName = it.name
                    navHostController.navigate(
                        NavigationItem.Detail.route
                    )
                }

                MainEvent.ToFavorite -> {
                    navHostController.navigate(
                        NavigationItem.Favorite.route
                    )
                }
            }
        }.launchIn(this)
    }

    MainContent(
        state = state,
        onAction = viewModel::processAction,
    )
}