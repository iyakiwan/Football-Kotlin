package com.muftialies.kotlin.submissionfootball.data

import com.google.gson.annotations.SerializedName

data class LeagueStanding (
    @SerializedName("idTeam")
    var standingTeamId: String? = null,

    @SerializedName("name")
    var standingTeamName: String? = null,

    @SerializedName("played")
    var standingTeamPlayed: String? = null,

    @SerializedName("goalsfor")
    var standingTeamGoalFor: String? = null,

    @SerializedName("goalsagainst")
    var standingTeamGoalAgainst: String? = null,

    @SerializedName("goalsdifference")
    var standingTeamGoalDifference: String? = null,

    @SerializedName("win")
    var standingTeamTotalWin: String? = null,

    @SerializedName("draw")
    var standingTeamTotalDraw: String? = null,

    @SerializedName("loss")
    var standingTeamTotalLose: String? = null,

    @SerializedName("total")
    var standingTeamTotalPoint: String? = null,

    var standingTeamRank: Int? = 0
)