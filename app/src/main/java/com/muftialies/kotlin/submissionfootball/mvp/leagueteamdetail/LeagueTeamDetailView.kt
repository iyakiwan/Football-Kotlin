package com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail

import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.data.LeaguePlayer

interface LeagueTeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetailTeam(data: List<LeagueDetailTeam>, status: Boolean)
    fun showLeagueDetailPlayer(data: List<LeaguePlayer>, status: Boolean)
}