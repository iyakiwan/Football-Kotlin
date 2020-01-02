package com.muftialies.kotlin.submissionfootball.mvp.leaguematch

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.TestContextProvider
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApiTest.Companion.leagueId
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApiTest.Companion.querySearch
import com.muftialies.kotlin.submissionfootball.data.LeagueMatch
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueMatchPresenterTest {

    @Mock
    private lateinit var view: LeagueMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    lateinit var presenter: LeagueMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = LeagueMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueMatchPast() {
        val leagueMatchPast: MutableList<LeagueMatch> = mutableListOf()
        val response = LeagueMatchResponse(leagueMatchPast, emptyList())

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(TheSportDBApi.getLeaguePreviousMatch(leagueId))).thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueMatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueMatchPast(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showDetailMatch(leagueMatchPast, true)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getLeagueMatchNext() {
        val leagueMatchNext: MutableList<LeagueMatch> = mutableListOf()
        val response = LeagueMatchResponse(leagueMatchNext, emptyList())

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(TheSportDBApi.getLeagueNextMatch(leagueId))).thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueMatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueMatchNext(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showDetailMatch(leagueMatchNext, true)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getLeagueMatchSearchResults() {
        val leagueMatchSearch: MutableList<LeagueMatch> = mutableListOf()
        val response = LeagueMatchResponse(emptyList(), leagueMatchSearch)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(TheSportDBApi.getLeagueMatchSearch(querySearch))).thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueMatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueMatchSearchResults(querySearch)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showDetailMatch(leagueMatchSearch, true)
            Mockito.verify(view).hideLoading()
        }
    }
}