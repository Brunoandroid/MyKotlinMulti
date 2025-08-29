package com.example.mykoltinmulti.repository

import com.example.mykoltinmulti.network.ApiService
import com.example.mykoltinmulti.network.model.QuoteResponse

class QuoteRepository(
    private val apiService: ApiService = ApiService()
) {
    suspend fun getQuote(): QuoteResponse {
        return apiService.getQuote()
    }
}
