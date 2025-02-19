package com.`fun`.pokemon.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.`fun`.pokemon.component.Loading
import com.`fun`.pokemon.data.model.PokemonData
import com.`fun`.pokemon.presentation.main.model.MainAction
import com.`fun`.pokemon.presentation.main.model.MainEvent
import com.`fun`.pokemon.presentation.main.model.MainViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    state: MainViewState,
    onAction: (MainAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Pokemon!", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                actions = {
                    IconButton(onClick = {
                        onAction(MainAction.OnFavClick)
                    }) {
                        Icon(
                            Icons.Filled.Settings,
                            tint = Color.White,
                            contentDescription = null
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(state.items) { pokemon ->
                        PokemonItem(pokemon, onAction)
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

@Composable
fun PokemonItem(pokemon: PokemonData, onAction: (MainAction) -> Unit) {

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp)),
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAction(MainAction.OnItemClick(pokemon.name))
            }) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = "Pokemon Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
            ) {
                Text(
                    text = pokemon.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
@Preview
fun PokemonItemPreview() {
    PokemonItem(
        PokemonData(
            name = "Goli",
            url = "",
            imageUrl = "",
        )
    ) {}
}
