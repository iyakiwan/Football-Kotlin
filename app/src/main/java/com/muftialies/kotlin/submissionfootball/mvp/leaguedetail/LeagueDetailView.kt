package com.muftialies.kotlin.submissionfootball.mvp.leaguedetail

import com.muftialies.kotlin.submissionfootball.data.LeagueDetail

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailLeague(data: List<LeagueDetail>)
}