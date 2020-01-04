package com.muftialies.kotlin.submissionfootball.mvp.leaguestanding

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueStandingResponse
import com.muftialies.kotlin.submissionfootball.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueStandingPresenter(
    private val view: LeagueStandingView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueStandings(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequestAsync(TheSportDBApi.getLeagueStandings(leagueId)).await(),
                LeagueStandingResponse::class.java
            )

            try {
                view.showLeagueStanding(data.table, true)
            } catch (e: Exception) {
                view.showLeagueStanding(emptyList(), false)
            }

            view.hideLoading()
        }
    }
}