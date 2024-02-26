package com.example.mvvmexample.domain

import com.example.mvvmexample.data.repository.QuoteRepository
import com.example.mvvmexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetQuotesUseCaseTest {
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    lateinit var getQuoteUseCase: GetQuotesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getQuoteUseCase = GetQuotesUseCase(quoteRepository)
    }

    @Test
    fun `when Api return nothing then get values from Db`() = runBlocking {
        //Given
        // coEvery == every pero con corrutinas
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()

        //When
        getQuoteUseCase()

        //Then
        // coVerify == verify pero con corrutinas
        // exactly = 1 verifica que sea llamado solo una vez
        coVerify(exactly = 1) {
            quoteRepository.getAllQuotesDatabase()
        }
    }

    @Test
    fun `when Api return something then get values from api and insert in db`() = runBlocking {
        //Given
        val myList = listOf(Quote("hola soy una cita", "Lautaro"))

        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList

        //When
        val response = getQuoteUseCase()

        //Then
        coVerify(exactly = 1) { quoteRepository.clearQuotes() }
        coVerify(exactly = 1) { quoteRepository.insertQuotes(any()) }
        coVerify(exactly = 0) { quoteRepository.getAllQuotesDatabase() }
        assert(myList == response)
    }
}