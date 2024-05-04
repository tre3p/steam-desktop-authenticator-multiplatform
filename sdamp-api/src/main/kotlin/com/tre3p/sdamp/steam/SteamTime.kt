package com.tre3p.sdamp.steam

import com.tre3p.sdamp.http.RetryableHttpClient
import com.tre3p.sdamp.steam.dto.TimeQueryResponse
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

private const val STEAM_SERVER_TIME_API_URL = "https://api.steampowered.com/ITwoFactorService/QueryTime/v0001?steamid=0"

open class SteamTime {
    companion object Default : SteamTime()

    fun getCurrentSteamChunk(): Long {
        val currentTime = System.currentTimeMillis().toUnixTime() + SteamTimeAligner.timeDifference
        val currentSteamChunk = currentTime / 30
        val secondsUntilChunkChange = currentTime - (currentSteamChunk * 30)

        return 30 - secondsUntilChunkChange
    }

    fun getTimeAlignedWithSteamServer(): Long = System.currentTimeMillis().toUnixTime() + SteamTimeAligner.timeDifference

    private open class SteamTimeAligner {
        val timeDifference: Long = getTimeDiffWithSteamServer()

        companion object Default : SteamTimeAligner()

        fun getTimeDiffWithSteamServer(): Long {
            val currentTimeUnix = System.currentTimeMillis().toUnixTime()
            val steamTimeMillis = getSteamServerTime()

            return steamTimeMillis - currentTimeUnix
        }

        private fun getSteamServerTime(): Long =
            runBlocking {
                val steamServerResponse =
                    RetryableHttpClient.executeRequest(
                        STEAM_SERVER_TIME_API_URL,
                        requestMethod = HttpMethod.Post,
                    )

                val timeQueryResponse = steamServerResponse.body<TimeQueryResponse>()
                val steamServerTime = timeQueryResponse.response.serverTime.toLong()
                System.currentTimeMillis().toUnixTime() - steamServerTime
            }
    }
}

fun Long.toUnixTime() = this / 1000L