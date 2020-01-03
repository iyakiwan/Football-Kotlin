package com.muftialies.kotlin.submissionfootball.data

import com.google.gson.annotations.SerializedName

data class LeagueTeam(
    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)