package com.example.mvvmexample.domain.model

import com.example.mvvmexample.data.database.entities.QuoteEntity
import com.example.mvvmexample.data.model.QuoteModel

data class Quote(
    val quote: String,
    val author: String
)


fun QuoteModel.toDomain() = Quote(quote, author)

fun QuoteEntity.toDomain() = Quote(quote, author)