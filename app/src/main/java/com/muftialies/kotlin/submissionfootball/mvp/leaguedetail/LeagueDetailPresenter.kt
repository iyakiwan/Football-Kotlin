package com.muftialies.kotlin.submissionfootball.mvp.leaguedetail

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueDetailPresenter(private val view: LeagueDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson) {

    fun getLeagueDetail(leagueId: String?) {
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueDetail(leagueId)),
                LeagueDetailResponse::class.java
            )

            uiThread {
                view.showDetailLeague(data.leagues)
            }
        }
    }

}