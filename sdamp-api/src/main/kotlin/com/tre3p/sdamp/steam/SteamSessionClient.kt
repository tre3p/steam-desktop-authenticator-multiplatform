package com.tre3p.sdamp.steam

import com.tre3p.sdamp.http.RetryableHttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class SteamSessionClient(
    sessionClient: RetryableHttpClient? = null
) {
    private val steamSessionHttpClient = sessionClient ?: RetryableHttpClient()

    suspend fun apiCall(
        method: String,
        service: String,
        endpoint: String,
        version: String = "v1",
        params: Map<String, Any>
    ): HttpResponse {
        val requestUrl = "$STEAM_COMMUNITY_URL/$service/$endpoint/$version"
        return steamSessionHttpClient.executeRequest(
            requestUrl = requestUrl,
            requestMethod = HttpMethod.parse(method),
            requestBody = params,
            requestHeaders = getApiCallDefaultRequestHeaders()
        )
    }

    private fun getApiCallDefaultRequestHeaders(): Map<String, String> {
        return mapOf(
            "Referer" to "$STEAM_COMMUNITY_URL/",
            "Origin" to STEAM_COMMUNITY_URL
        )
    }
}