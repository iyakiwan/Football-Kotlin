package com.muftialies.kotlin.submissionfootball.data

import com.google.gson.annotations.SerializedName

data class LeagueMatch(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("dateEvent")
    var eventDate: String? = null,

    @SerializedName("strHomeTeam")
    var eventHomeTeam: String? = null,

    @SerializedName("intHomeScore")
    var eventHomeScore: String? = null,

    @SerializedName("strAwayTeam")
    var eventAwayTeam: String? = null,

    @SerializedName("intAwayScore")
    var eventAwayScore: String? = null
)