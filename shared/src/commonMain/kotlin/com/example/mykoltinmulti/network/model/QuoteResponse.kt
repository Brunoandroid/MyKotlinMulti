package com.example.mykoltinmulti.network.model

import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val quoteText: String,
    val quoteAuthor: String,
    val senderName: String? = null,
    val senderLink: String? = null,
    val quoteLink: String
)