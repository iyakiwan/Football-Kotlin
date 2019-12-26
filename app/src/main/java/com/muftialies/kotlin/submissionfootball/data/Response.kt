package com.muftialies.kotlin.submissionfootball.data

data class LeagueDetailResponse(
    val leagues: List<LeagueDetail>)

data class LeagueMatchResponse(
    val events: List<LeagueMatch>)

data class LeagueMatchDetailResponse(
    val events: List<LeagueMatchDetail>,
    val teams: List<LeagueDetailTeam>
)