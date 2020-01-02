package com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.TestContextProvider
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApiTest.Companion.eventId
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApiTest.Companion.teamId
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchDetail
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchDetailResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueMatchDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueMatchDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    lateinit var presenter: LeagueMatchDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = LeagueMatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueMatchDetail() {
        val leagueMatchDetail: MutableList<LeagueMatchDetail> = mutableListOf()
        val response = LeagueMatchDetailResponse(leagueMatchDetail, emptyList())

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(TheSportDBApi.getLeagueMatchDetail(eventId))).thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueMatchDetailResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueMatchDetail(eventId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showDetailMatchLeague(leagueMatchDetail)
        }
    }

    @Test
    fun getLeagueMatchTeam() {
        val leagueDetailTeam: MutableList<LeagueDetailTeam> = mutableListOf()
        val response = LeagueMatchDetailResponse(emptyList(),leagueDetailTeam)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(TheSportDBApi.getLeagueTeamDetail(teamId))).thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueMatchDetailResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueMatchTeam(teamId, teamId)


            Mockito.verify(view).showDetailTeamLeague(leagueDetailTeam, leagueDetailTeam)
            Mockito.verify(view).hideLoading()
        }
    }
}