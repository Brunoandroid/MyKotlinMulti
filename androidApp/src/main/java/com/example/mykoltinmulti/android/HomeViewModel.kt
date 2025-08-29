package com.example.mykoltinmulti.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykoltinmulti.network.model.QuoteResponse
import com.example.mykoltinmulti.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val quoteRepository: QuoteRepository = QuoteRepository()

) : ViewModel() {
    private val _quote = MutableStateFlow<QuoteResponse?>(null)
    val quote: StateFlow<QuoteResponse?> = _quote

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchQuote()
    }

    fun fetchQuote() {
        viewModelScope.launch {
            try {
                val response = quoteRepository.getQuote()
                _quote.value = response
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Erro desconhecido"
            }
        }
    }
}

