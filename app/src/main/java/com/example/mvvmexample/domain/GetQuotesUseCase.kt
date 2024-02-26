package com.example.mvvmexample.domain

import com.example.mvvmexample.data.database.entities.toDatabase
import com.example.mvvmexample.data.repository.QuoteRepository
import com.example.mvvmexample.domain.model.Quote
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(
   private val repository: QuoteRepository
) {
    suspend operator fun invoke(): List<Quote>? {
        val quotes = repository.getAllQuotesFromApi()
        return if (quotes.isNotEmpty()){
            repository.clearQuotes()
            repository.insertQuotes(quotes.map { it.toDatabase() })
            quotes
        }else{
            repository.getAllQuotesDatabase()
        }
    }
}