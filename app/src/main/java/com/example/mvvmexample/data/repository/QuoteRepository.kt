package com.example.mvvmexample.data.repository

import com.example.mvvmexample.data.database.dao.QuoteDao
import com.example.mvvmexample.data.database.entities.QuoteEntity
import com.example.mvvmexample.data.network.QuoteService
import com.example.mvvmexample.domain.model.Quote
import com.example.mvvmexample.domain.model.toDomain
import javax.inject.Inject


class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {

    suspend fun getAllQuotesFromApi(): List<Quote>{
        val response = api.getQuotes()
        return response.map {
            it.toDomain()
        }
    }

    suspend fun getAllQuotesDatabase(): List<Quote>{
        val response = quoteDao.getAllQuotes()
        return response.map {
            it.toDomain()
        }
    }

    suspend fun insertQuotes(quotes: List<QuoteEntity>){
        quoteDao.insertAll(quotes)
    }

    suspend fun clearQuotes(){
        quoteDao.deleteAllQuotes()
    }
}