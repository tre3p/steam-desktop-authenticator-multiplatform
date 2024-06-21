package com.tre3p.sdamp.steam

import com.tre3p.sdamp.http.RetryableHttpClient
import com.tre3p.sdamp.steam.dto.TimeQueryResponse
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

private const val STEAM_SERVER_TIME_API_URL = "https://api.steampowered.com/ITwoFactorService/QueryTime/v0001?steamid=0"

open class SteamTime {
    companion object Default : SteamTime()

    private val httpClient = RetryableHttpClient()
    private val timeDifference: Long = getTimeDiffWithSteamServer()
    val alignedTime: Long
        get() = System.currentTimeMillis().toUnixTime() + timeDifference

    fun getCurrentSteamChunk(): Long {
        val steamServerTime = alignedTime
        val currentSteamChunk = steamServerTime / 30
        val secondsUntilChunkChange = steamServerTime - (currentSteamChunk * 30)

        return 30 - secondsUntilChunkChange
    }

    fun getTimeDiffWithSteamServer(): Long {
        val currentTimeUnix = System.currentTimeMillis().toUnixTime()
        val steamTimeMillis = getSteamServerTime()

        return steamTimeMillis - currentTimeUnix
    }

    private fun getSteamServerTime(): Long =
        runBlocking {
            val steamServerResponse =
                httpClient.executeRequest(
                    requestUrl = STEAM_SERVER_TIME_API_URL,
                    requestMethod = HttpMethod.Post,
                )

            val timeQueryResponse = steamServerResponse.body<TimeQueryResponse>()
            timeQueryResponse.response.serverTime.toLong()
        }
}

fun Long.toUnixTime() = this / 1000L