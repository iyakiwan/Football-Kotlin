package com.muftialies.kotlin.submissionfootball.favorite

data class FavoriteMatch(
    val id: Long?,
    var favoriteDetailId: String? = null,
    var favoriteDetailName: String? = null,
    var favoriteDetailDate: String? = null,
    var favoriteDetailTime: String? = null,
    var favoriteDetailRound: String? = null,
    var favoriteDetailSeason: String? = null,
    var favoriteDetailHomeId: String? = null,
    var favoriteDetailHomeTeam: String? = null,
    var favoriteDetailHomeScore: String? = null,
    var favoriteDetailHomeGoal: String? = null,
    var favoriteDetailHomeRed: String? = null,
    var favoriteDetailHomeYellow: String? = null,
    var favoriteDetailHomeLogo: String? = null,
    var favoriteDetailAwayId: String? = null,
    var favoriteDetailAwayTeam: String? = null,
    var favoriteDetailAwayScore: String? = null,
    var favoriteDetailAwayGoal: String? = null,
    var favoriteDetailAwayRed: String? = null,
    var favoriteDetailAwayYellow: String? = null,
    var favoriteDetailAwayLogo: String? = null
) {
    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val MATCH_NAME: String = "MATCH_NAME"
        const val MATCH_DATE: String = "MATCH_DATE"
        const val MATCH_TIME: String = "MATCH_TIME"
        const val MATCH_ROUND: String = "MATCH_ROUND"
        const val MATCH_SESSION: String = "MATCH_SESSION"

        //HOME
        const val MATCH_HOME_ID: String = "MATCH_HOME_ID"
        const val MATCH_HOME_TEAM: String = "MATCH_HOME_TEAM"
        const val MATCH_HOME_SCORE: String = "MATCH_HOME_SCORE"
        const val MATCH_HOME_GOAL: String = "MATCH_HOME_GOAL"
        const val MATCH_HOME_RED: String = "MATCH_HOME_RED"
        const val MATCH_HOME_YELLOW: String = "MATCH_HOME_YELLOW"
        const val MATCH_HOME_LOGO: String = "MATCH_HOME_LOGO"

        //AWAY
        const val MATCH_AWAY_ID: String = "MATCH_AWAY_ID"
        const val MATCH_AWAY_TEAM: String = "MATCH_AWAY_TEAM"
        const val MATCH_AWAY_SCORE: String = "MATCH_AWAY_SCORE"
        const val MATCH_AWAY_GOAL: String = "MATCH_AWAY_GOAL"
        const val MATCH_AWAY_RED: String = "MATCH_AWAY_RED"
        const val MATCH_AWAY_YELLOW: String = "MATCH_AWAY_YELLOW"
        const val MATCH_AWAY_LOGO: String = "MATCH_AWAY_LOGO"
    }
}