package com.`fun`.pokemon.presentation.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.`fun`.pokemon.presentation.detail.model.DetailAction
import com.`fun`.pokemon.presentation.detail.model.DetailContentArgs
import com.`fun`.pokemon.presentation.detail.model.DetailEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val selectedItem = DetailContentArgs.pokemonName

    val context = LocalContext.current

    if (selectedItem.isEmpty()) {
        Log.i("Error", "Args not found")
        navHostController.popBackStack()
        return
    }

    val state by remember { viewModel.state }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processAction(DetailAction.Init(selectedItem))
        viewModel.events.onEach {
            when (it) {
                DetailEvent.GoBack -> {
                    navHostController.navigateUp()
                }

                is DetailEvent.OnError -> Toast.makeText(
                    context,
                    it.msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(this)
    }

    DetailContent(
        state = state,
        onAction = viewModel::processAction,
    )
}