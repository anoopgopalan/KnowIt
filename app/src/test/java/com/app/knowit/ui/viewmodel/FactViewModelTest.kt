package com.app.knowit.ui.viewmodel

import com.app.knowit.R
import com.app.knowit.data.Fact
import com.app.knowit.data.FactRepository
import com.app.knowit.data.Info
import com.app.knowit.ui.ImmediateSchedulersRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class FactViewModelTest {

    @get: Rule
    val immediateSchedulersRule = ImmediateSchedulersRule()

    private lateinit var factViewModelUnderTest: FactViewModel
    private val factRepository: FactRepository = mock()
    private val mockFact: Fact = mock()
    private val newsRow: List<Info> = listOf()
    private val title = "title"
    private lateinit var loadFactsTestObserver: TestObserver<List<Info>>
    private lateinit var titleUpdateTestObserver: TestObserver<String>
    private lateinit var errorMessageShowsTestObserver: TestObserver<Int>
    private val errorMessage: Int = R.string.something_went_wrong_try_again_later

    @Before
    fun setup(){
        whenever(factRepository.getFacts())
            .thenReturn(Single.just(mockFact))
        whenever(mockFact.rows)
            .thenReturn(newsRow)
        whenever(mockFact.title)
            .thenReturn(title)

        factViewModelUnderTest = FactViewModel(factRepository)

        loadFactsTestObserver = factViewModelUnderTest.factLoads.test()
        titleUpdateTestObserver = factViewModelUnderTest.titleUpdates.test()
        errorMessageShowsTestObserver = factViewModelUnderTest.errorMessageShows.test()
    }

    @Test
    fun `refresh the data when swipe to refresh initiates`(){
        whenever(factRepository.getFacts())
            .thenReturn(Single.just(mockFact))
        whenever(mockFact.rows)
            .thenReturn(newsRow)
        whenever(mockFact.title)
            .thenReturn(title)

        factViewModelUnderTest.swipedToRefreshData()

        loadFactsTestObserver.assertValue(newsRow)
        titleUpdateTestObserver.assertValue(title)
    }

    @Test
    fun `loading facts`(){
        whenever(factRepository.getFacts())
            .thenReturn(Single.just(mockFact))
        whenever(mockFact.rows)
            .thenReturn(newsRow)
        whenever(mockFact.title)
            .thenReturn(title)

        factViewModelUnderTest.loadFacts()

        loadFactsTestObserver.assertValue(newsRow)
        titleUpdateTestObserver.assertValue(title)
    }

    @Test
    fun `fetching news failed with exception`(){
        whenever(factRepository.getFacts())
            .thenReturn(Single.error(Exception("error")))

        factViewModelUnderTest.swipedToRefreshData()

        errorMessageShowsTestObserver.assertValue(errorMessage)

    }
}