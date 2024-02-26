package com.example.mvvmexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.domain.GetQuotesUseCase
import com.example.mvvmexample.domain.GetRandomQuoteUseCase
import com.example.mvvmexample.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
) : ViewModel() {

    val quoteModel = MutableLiveData<Quote>()

    val isLoading = MutableLiveData<Boolean>()


    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            // como el caso de uso tiene el metodo invoke, de esta forma se ejecuta automaticamente
            val result = getQuotesUseCase()
            isLoading.postValue(false)
            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result.first())
            }

        }
    }

    fun randomQuote() {

        viewModelScope.launch {
            isLoading.postValue(true)

            val quote = getRandomQuoteUseCase()
            if (quote != null) {
                quoteModel.postValue(quote)
            }
            isLoading.postValue(false)
        }


    }


}