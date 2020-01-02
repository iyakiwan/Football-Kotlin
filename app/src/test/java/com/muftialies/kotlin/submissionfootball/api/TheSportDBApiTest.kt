package com.muftialies.kotlin.submissionfootball.api

import org.junit.Test

import org.mockito.Mockito

class TheSportDBApiTest {

    companion object {
        const val leagueId = "4328"
        const val eventId = "602306"
        const val teamId = "133619"
        const val querySearch = "United"
    }



    @Test
    fun getLeagueDetail() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueDetail(leagueId)

        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }

    @Test
    fun getLeagueNextMatch() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueNextMatch(leagueId)

        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }

    @Test
    fun getLeaguePreviousMatch() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeaguePreviousMatch(leagueId)

        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }

    @Test
    fun getLeagueMatchDetail() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueMatchDetail(eventId)

        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }

    @Test
    fun getLeagueTeamDetail() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueTeamDetail(teamId)

        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }

    @Test
    fun getLeagueMatchSearch() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = TheSportDBApi.getLeagueMatchSearch(querySearch)

        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }
}