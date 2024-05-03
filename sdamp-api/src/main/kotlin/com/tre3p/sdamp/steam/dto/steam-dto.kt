package com.tre3p.sdamp.steam.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeQueryResponse(
    @SerialName("response") val response: Response,
) {
    @Serializable
    data class Response(
        @SerialName("server_time")
        val serverTime: String,
    )
}
