package com.muftialies.kotlin.submissionfootball.mvp.leaguematch

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchResponse
import com.muftialies.kotlin.submissionfootball.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueMatchPresenter(
    private val view: LeagueMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeagueMatchPast(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeaguePreviousMatch(leagueId)).await(),
                LeagueMatchResponse::class.java
            )

            try {
                view.showDetailMatch(data.events, true)
            } catch (e: Exception) {
                view.showDetailMatch(emptyList(), false)
            }
            view.hideLoading()
        }
    }

    fun getLeagueMatchNext(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueNextMatch(leagueId)).await(),
                LeagueMatchResponse::class.java
            )

            try {
                view.showDetailMatch(data.events, true)
            } catch (e: Exception) {
                view.showDetailMatch(emptyList(), false)
            }
            view.hideLoading()
        }
    }

    fun getLeagueMatchSearchResults(searchKey: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueMatchSearch(searchKey)).await(),
                LeagueMatchResponse::class.java
            )

            try {
                view.showDetailMatch(data.event, true)
            } catch (e: Exception) {
                view.showDetailMatch(emptyList(), false)
            }
            view.hideLoading()
        }
    }
}