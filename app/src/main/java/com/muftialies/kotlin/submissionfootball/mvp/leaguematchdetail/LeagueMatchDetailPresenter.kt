package com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueMatchDetailPresenter(private val view: LeagueMatchDetailView,
                                 private val apiRepository: ApiRepository,
                                 private val gson: Gson) {

    fun getLeagueMatchDetail(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueMatchDetail(leagueId)).await(),
                LeagueMatchDetailResponse::class.java
            )

            view.showDetailMatchLeague(data.events)
        }
    }

    fun getLeagueMatchTeam(teamId1: String?, teamId2: String?) {
        GlobalScope.launch(Dispatchers.Main){
            val data1 = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueTeamDetail(teamId1)).await(),
                LeagueMatchDetailResponse::class.java
            )

            val data2 = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueTeamDetail(teamId2)).await(),
                LeagueMatchDetailResponse::class.java
            )

            view.showDetailTeamLeague(data1.teams, data2.teams)
            view.hideLoading()
        }
    }
}