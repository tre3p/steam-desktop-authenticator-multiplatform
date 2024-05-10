package com.tre3p.sdamp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.misc.START_PADDING
import com.tre3p.sdamp.steam.SteamTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthCodeProgressBar() {
    val steamChunkProgressBar = remember { mutableStateOf(1.0f) }
    rememberCoroutineScope().launch {
        while (true) {
            // Since steam chunk values are in range 0..30 - dividing by 30f makes this values usable by progress bar
            steamChunkProgressBar.value = SteamTime.getCurrentSteamChunk() / 30f
            delay(500)
        }
    }

    LinearProgressIndicator(
        progress = steamChunkProgressBar.value,
        color = Color.Gray,
        modifier = Modifier
            .padding(start = START_PADDING, end = 112.dp)
            .fillMaxWidth()
    )
}