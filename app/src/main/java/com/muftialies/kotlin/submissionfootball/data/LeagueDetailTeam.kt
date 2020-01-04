package com.muftialies.kotlin.submissionfootball.data

import com.google.gson.annotations.SerializedName

data class LeagueDetailTeam (
    @SerializedName("idTeam")
    var teamDetailId: String? = null,

    @SerializedName("strTeam")
    var teamDetailName: String? = null,

    @SerializedName("strTeamBadge")
    var teamDetailLogo: String? = null,

    @SerializedName("intFormedYear")
    var teamDetailFormedYear: String? = null,

    @SerializedName("strStadium")
    var teamDetailStadium: String? = null,

    @SerializedName("strDescriptionEN")
    var teamDetailDescription: String? = null
)