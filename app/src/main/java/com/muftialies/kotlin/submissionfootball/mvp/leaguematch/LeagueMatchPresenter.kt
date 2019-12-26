package com.muftialies.kotlin.submissionfootball.mvp.leaguematch

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueMatchPresenter(private val view: LeagueMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getLeagueMatchPast(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeaguePreviousMatch(leagueId)),
                LeagueMatchResponse::class.java
            )

            uiThread {
                view.showDetailMatch(data.events)
                view.hideLoading()
            }
        }
    }

    fun getLeagueMatchNext(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueNextMatch(leagueId)),
                LeagueMatchResponse::class.java
            )

            uiThread {
                try {
                    view.showDetailMatch(data.events)
                } catch (e: Exception){

                }
                view.hideLoading()
            }
        }
    }
}