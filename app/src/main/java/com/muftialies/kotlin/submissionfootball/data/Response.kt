package com.muftialies.kotlin.submissionfootball.data

data class LeagueDetailResponse(
    val leagues: List<LeagueDetail>)

data class LeagueMatchResponse(
    val events: List<LeagueMatch>,
    val event: List<LeagueMatch>)

data class LeagueMatchDetailResponse(
    val events: List<LeagueMatchDetail>,
    val teams: List<LeagueDetailTeam>)

data class LeagueTeamResponse(
    val teams: List<LeagueTeam>)

data class LeagueStandingResponse(
    val table: List<LeagueStanding>,
    val teams: List<LeagueDetailTeam>)

data class TeamDetailResponse(
    val teams: List<LeagueDetailTeam>,
    val player: List<LeaguePlayer>)