package com.muftialies.kotlin.submissionfootball.favorite

data class FavoriteTeam(
    val id: Long?,
    var favoriteTeamId: String? = null,
    var favoriteTeamName: String? = null,
    var favoriteTeamLogo: String? = null,
    var favoriteTeamYear: String? = null,
    var favoriteTeamStadium: String? = null,
    var favoriteTeamDesc: String? = null
) {
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_LOGO: String = "TEAM_LOGO"
        const val TEAM_YEAR: String = "TEAM_YEAR"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
        const val TEAM_DESC: String = "TEAM_DESC"
    }
}