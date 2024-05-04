package com.tre3p.sdamp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.misc.START_PADDING

@Composable
fun AuthCodeProgressBar() {
    LinearProgressIndicator(
        progress = 1.0f,
        color = Color.Gray,
        modifier = Modifier
            .padding(start = START_PADDING, end = 112.dp)
            .fillMaxWidth()
    )
}