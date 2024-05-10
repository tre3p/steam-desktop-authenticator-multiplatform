package com.tre3p.sdamp.steam

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SteamChunkChangeListener(
    val actionOnChunkChange: () -> Unit
) {
    private val steamChunkDuration = 30L

    init {
        GlobalScope.launch {
            initChunkChangeListener()
        }
    }

    private suspend fun initChunkChangeListener() = coroutineScope {
        // Since Steam doesn't return milliseconds in response - we need to synchronize milliseconds till chunk change manually
        val steamChunkOnInit = SteamTime.getCurrentSteamChunk()
        var refreshedSteamChunk: Long = steamChunkOnInit
        while (refreshedSteamChunk == steamChunkOnInit) {
            refreshedSteamChunk = SteamTime.getCurrentSteamChunk()
        }

        // Wait for current chunk to complete
        delay(refreshedSteamChunk * 1000)

        // Then wait regular 30 seconds which each chunk takes
        while(true) {
            actionOnChunkChange.invoke()
            delay(steamChunkDuration * 1000)
        }
    }
}