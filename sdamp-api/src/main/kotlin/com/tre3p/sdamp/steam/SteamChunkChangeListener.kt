package com.tre3p.sdamp.steam

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SteamChunkChangeListener(
    val actionOnChunkChange: () -> Unit
) {
    private val steamChunkOnInit: Long = SteamTime.getCurrentSteamChunk()
    private val steamChunkDuration = 30L

    init {
        GlobalScope.launch {
            initChunkChangeListener()
        }
    }

    private suspend fun initChunkChangeListener() = coroutineScope {
        // Wait for current chunk to complete
        delay(steamChunkOnInit * 1000)

        // Then wait regular 30 seconds which each chunk takes
        while(true) {
            actionOnChunkChange.invoke()
            delay(steamChunkDuration * 1000)
        }
    }
}