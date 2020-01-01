package com.muftialies.kotlin.submissionfootball.api

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class TheSportDBApiTest {

    private val leagueId = "4328"
    private val eventId = "602306"
    private val teamId = "133619"
    private val querySearch = "United"


    @Test
    fun getLeagueDetail() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueDetail(leagueId)

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun getLeagueNextMatch() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueNextMatch(leagueId)

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun getLeaguePreviousMatch() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeaguePreviousMatch(leagueId)

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun getLeagueMatchDetail() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueMatchDetail(eventId)

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun getLeagueTeamDetail() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueTeamDetail(teamId)

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun getLeagueMatchSearch() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueMatchSearch(querySearch)

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }
}