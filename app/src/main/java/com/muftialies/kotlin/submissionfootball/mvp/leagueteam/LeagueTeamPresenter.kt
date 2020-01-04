package com.muftialies.kotlin.submissionfootball.mvp.leagueteam

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueTeamResponse
import com.muftialies.kotlin.submissionfootball.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueTeamPresenter(
    private val view: LeagueTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueTeams(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueTeamLists(leagueId)).await(),
                LeagueTeamResponse::class.java
            )

            try {
                view.showLeagueTeam(data.teams, true)
            } catch (e: Exception) {
                view.showLeagueTeam(emptyList(), false)
            }
            view.hideLoading()
        }
    }

    fun getLeagueSearchTeams(searchKey: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueTeamSearch(searchKey)).await(),
                LeagueTeamResponse::class.java
            )

            try {
                view.showLeagueTeam(data.teams, true)
            } catch (e: Exception) {
                view.showLeagueTeam(emptyList(), false)
            }
            view.hideLoading()
        }
    }
}