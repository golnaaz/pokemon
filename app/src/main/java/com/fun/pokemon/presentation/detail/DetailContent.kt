package com.`fun`.pokemon.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.`fun`.pokemon.R
import com.`fun`.pokemon.component.Loading
import com.`fun`.pokemon.data.model.PokemonInfoData
import com.`fun`.pokemon.presentation.detail.model.DetailAction
import com.`fun`.pokemon.presentation.detail.model.DetailViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    state: DetailViewState,
    onAction: (DetailAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.name, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(DetailAction.GoBack) }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp),
                contentAlignment = Alignment.Center,
            ) {
                FavoriteItem(
                    modifier = Modifier.fillMaxSize(),
                    item = state.item,
                    isFavorite = state.isFavorite,
                    onAction = {
                        onAction(DetailAction.ToggleFavorite(state.isFavorite))
                    })

                if (state.isLoading) {
                    Loading()
                }
            }
        }
    )
}

@Composable
fun FavoriteItem(
    modifier: Modifier,
    item: PokemonInfoData,
    isFavorite: Boolean,
    onAction: () -> Unit
) {
    Column(modifier = modifier) {
        Row {
            Text("Name: ".plus(item.name), modifier = Modifier.weight(1f))
            Image(
                painter =
                if (isFavorite) painterResource(id = R.drawable.ic_favorite)
                else painterResource(
                    id = R.drawable.ic_unfavorite
                ),
                contentDescription = "Favorite",
                modifier = Modifier.clickable {
                    onAction()
                }
            )
        }
        Text("Height: ".plus(item.height))
        Text("Weight: ".plus(item.weight))
        Text("Experience: ".plus(item.experience))
    }
}
