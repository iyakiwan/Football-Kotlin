package com.muftialies.kotlin.submissionfootball.mvp.leaguematch

import com.muftialies.kotlin.submissionfootball.data.LeagueMatch

interface LeagueMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetailMatch(data: List<LeagueMatch>, status: Boolean)
}