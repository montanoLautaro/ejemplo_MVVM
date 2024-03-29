package com.example.mvvmexample.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmexample.domain.GetQuotesUseCase
import com.example.mvvmexample.domain.GetRandomQuoteUseCase
import com.example.mvvmexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class QuoteViewModelTest {
    private lateinit var quoteViewModel: QuoteViewModel

    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    // regla para testear los LiveData
    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuotesUseCase, getRandomQuoteUseCase)

        // emula la corrutina de tipo viewModel
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        // una vez terminado el test, termina con termina con la corrutina
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all quotes and set the first value`() =
        runTest {
            //Given
            val quotes = listOf(Quote("Soy una cita", "Lautaro"), Quote("Soy otra cita", "Otro"))

            coEvery { getQuotesUseCase() } returns quotes

            //When
            quoteViewModel.onCreate()

            //Then
            assert(quoteViewModel.quoteModel.value == quotes.first())
        }

    @Test
    fun `when randomQuoteUseCase return a quote set on the livedata`() = runTest {
        //Given
        val quote = Quote("Soy una cita", "Lautaro")

        coEvery {
            getRandomQuoteUseCase()
        } returns quote

        //When
        quoteViewModel.randomQuote()

        //Then
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `if randomQuoteUseCase return null, keep the last value`() = runTest {
        //Given
        val quote = Quote("Soy una cita", "Lautaro")
        quoteViewModel.quoteModel.value = quote

        coEvery { getRandomQuoteUseCase() } returns null

        //When
        quoteViewModel.randomQuote()


        //Then
        assert(quoteViewModel.quoteModel.value == quote)
    }

}