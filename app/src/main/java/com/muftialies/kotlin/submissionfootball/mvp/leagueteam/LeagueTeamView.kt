package com.muftialies.kotlin.submissionfootball.mvp.leagueteam

import com.muftialies.kotlin.submissionfootball.data.LeagueTeam

interface LeagueTeamView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueTeam(data: List<LeagueTeam>, status: Boolean)
}