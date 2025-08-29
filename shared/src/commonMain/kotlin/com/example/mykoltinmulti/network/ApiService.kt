package com.example.mykoltinmulti.network

import com.example.mykoltinmulti.network.model.QuoteResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val clientProvider: HttpClientProvider = HttpClientProvider) {

    suspend fun getQuote(): QuoteResponse {
        val url = "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en"
        val response = clientProvider.httpClient.get(url)
        return response.body()
    }
}
