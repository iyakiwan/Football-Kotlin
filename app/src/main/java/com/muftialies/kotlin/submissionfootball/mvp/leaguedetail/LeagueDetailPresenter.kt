package com.muftialies.kotlin.submissionfootball.mvp.leaguedetail

import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.api.TheSportDBApi
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailResponse
import com.muftialies.kotlin.submissionfootball.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(private val view: LeagueDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeagueDetail(leagueId: String?) {
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequestAsync(TheSportDBApi.getLeagueDetail(leagueId)).await(),
                LeagueDetailResponse::class.java
            )

            view.showDetailLeague(data.leagues)
        }
    }

}