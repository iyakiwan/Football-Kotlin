package com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.TeamDetailResponse
import com.muftialies.kotlin.submissionfootball.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueTeamDetailPresenter(
    private val view: LeagueTeamDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeagueDetailTeams(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueTeamDetail(leagueId)).await(),
                TeamDetailResponse::class.java
            )

            try {
                view.showLeagueDetailTeam(data.teams,emptyList(), true)
            } catch (e: Exception) {
                view.showLeagueDetailTeam(emptyList(), emptyList(),false)
            }
            view.hideLoading()
        }
    }
}