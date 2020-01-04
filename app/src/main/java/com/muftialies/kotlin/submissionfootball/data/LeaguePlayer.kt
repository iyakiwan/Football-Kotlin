package com.muftialies.kotlin.submissionfootball.data

import com.google.gson.annotations.SerializedName

data class LeaguePlayer (
    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strCutout")
    var playerPhoto: String? = null,

    @SerializedName("strSport")
    var playerSport: String? = null,

    @SerializedName("idTeam")
    var teamId: String? = null
)