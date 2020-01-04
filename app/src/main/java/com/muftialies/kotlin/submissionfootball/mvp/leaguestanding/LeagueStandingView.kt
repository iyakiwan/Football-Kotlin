package com.muftialies.kotlin.submissionfootball.mvp.leaguestanding

import com.muftialies.kotlin.submissionfootball.data.LeagueStanding

interface LeagueStandingView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueStanding(data: List<LeagueStanding>, status: Boolean)
}