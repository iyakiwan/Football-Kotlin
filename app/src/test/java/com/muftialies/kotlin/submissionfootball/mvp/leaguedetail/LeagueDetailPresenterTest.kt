package com.muftialies.kotlin.submissionfootball.mvp.leaguedetail

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.TestContextProvider
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApiTest.Companion.leagueId
import com.muftialies.kotlin.submissionfootball.data.LeagueDetail
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueDetail() {
        val leagueDetail: MutableList<LeagueDetail> = mutableListOf()
        val response = LeagueDetailResponse(leagueDetail)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(TheSportDBApi.getLeagueDetail(leagueId))).thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueDetailResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueDetail(leagueId)

            Mockito.verify(view).showDetailLeague(leagueDetail)
        }


    }
}