package com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail

import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam

interface LeagueTeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetailTeam(data1: List<LeagueDetailTeam>, data2: List<LeagueDetailTeam>, status: Boolean)
}