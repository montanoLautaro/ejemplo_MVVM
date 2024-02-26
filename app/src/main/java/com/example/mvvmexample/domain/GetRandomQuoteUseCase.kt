package com.example.mvvmexample.domain

import com.example.mvvmexample.data.repository.QuoteRepository
import com.example.mvvmexample.domain.model.Quote
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke() : Quote?{
        val quotes = repository.getAllQuotesDatabase()
        if (quotes.isNotEmpty()){
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}