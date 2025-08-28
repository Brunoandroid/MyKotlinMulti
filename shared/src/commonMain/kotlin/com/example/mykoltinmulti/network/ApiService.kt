package com.example.mykoltinmulti.network

import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val clientProvider: HttpClientProvider = HttpClientProvider) {
    suspend fun ping(url: String = "https://httpbin.org/get"): String {
        val response = clientProvider.httpClient.get(url)
        return response.body()
    }
}