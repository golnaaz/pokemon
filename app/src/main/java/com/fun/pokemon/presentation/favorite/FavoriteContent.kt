package com.`fun`.pokemon.presentation.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.`fun`.pokemon.component.Loading
import com.`fun`.pokemon.presentation.detail.model.DetailAction
import com.`fun`.pokemon.presentation.favorite.model.FavoriteAction
import com.`fun`.pokemon.presentation.favorite.model.FavoriteViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    state: FavoriteViewState,
    onAction: (FavoriteAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Pokemon Favorites", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                navigationIcon = {
                    IconButton(onClick = { onAction(FavoriteAction.GoBack) }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                LazyColumn {
                    items(state.items) { pokemon ->
                        Column(Modifier
                            .padding(20.dp)
                            .align(Alignment.Center)) {
                            Text(
                                text = pokemon.name,
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable {
                                        onAction(FavoriteAction.OnItemClick(pokemon.name))
                                    }
                            )
                        }
                    }
                }

                if (state.isLoading) {
                    Loading()
                }
                Text(text = state.error, modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}

