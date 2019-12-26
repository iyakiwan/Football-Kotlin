package com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail

import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchDetail

interface LeagueMatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailMatchLeague(data: List<LeagueMatchDetail>)
    fun showDetailTeamLeague(data1: List<LeagueDetailTeam>, data2: List<LeagueDetailTeam>)
}