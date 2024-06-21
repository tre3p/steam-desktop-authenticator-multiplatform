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

open class RetryableHttpClient(
    val retryCount: Int = 3,
    val retryDelayMs: Long = 500
): HttpClient {
    private val httpClient = HttpClient(CIO) {
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
    }

    suspend fun executeRequest(
        requestUrl: String,
        requestMethod: HttpMethod = HttpMethod.Get,
        requestBody: Any? = null,
        requestHeaders: Map<String, String>? = null
    ): HttpResponse {
        return httpClient.request(requestUrl) {
            method = requestMethod

            if (requestBody != null) {
                if (requestBody is String) {
                    contentType(ContentType.Application.Json)
                } else if (requestBody is Map<*, *>) {
                    contentType(ContentType.MultiPart.FormData)
                }

                setBody(requestBody)
            }

            requestHeaders?.map {
                headers {
                    append(it.key, it.value)
                }
            }
        }
    }
}
