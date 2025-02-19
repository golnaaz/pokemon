package com.`fun`.pokemon.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier
            .drawBehind {
                drawCircle(
                    Color.Black,
                    radius = size.width / 2 - 1.dp.toPx() / 2,
                    style = Stroke(1.dp.toPx())
                )
            },
        color = Color.LightGray,
        strokeWidth = 1.dp
    )
}