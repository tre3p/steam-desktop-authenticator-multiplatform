package com.tre3p.sdamp.http

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

open class RetryableHttpClient {
    companion object: RetryableHttpClient()

    suspend fun executeRequest(
        requestUrl: String,
        requestMethod: HttpMethod = HttpMethod.Get,
        retryCount: Int = 3,
        retryDelayMs: Long = 500,
    ): HttpResponse {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(HttpRequestRetry) {
                maxRetries = retryCount
                delayMillis { retryDelayMs }
            }
        }.use {
            it.request(requestUrl) { method = requestMethod }
        }
    }
}
