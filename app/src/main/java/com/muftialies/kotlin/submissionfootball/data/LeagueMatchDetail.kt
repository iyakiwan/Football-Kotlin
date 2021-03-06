package com.muftialies.kotlin.submissionfootball.data

import com.google.gson.annotations.SerializedName

data class LeagueMatchDetail (
    @SerializedName("idEvent")
    var eventDetailId: String? = null,

    @SerializedName("strEvent")
    var eventDetailName: String? = null,

    @SerializedName("strHomeTeam")
    var eventDetailHomeTeam: String? = null,

    @SerializedName("intHomeScore")
    var eventDetailHomeScore: String? = null,

    @SerializedName("strAwayTeam")
    var eventDetailAwayTeam: String? = null,

    @SerializedName("intAwayScore")
    var eventDetailAwayScore: String? = null,

    @SerializedName("dateEvent")
    var eventDetailDate: String? = null,

    @SerializedName("strTime")
    var eventDetailTime: String? = null,

    @SerializedName("intRound")
    var eventDetailRound: String? = null,

    @SerializedName("strSeason")
    var eventDetailSeason: String? = null,

    @SerializedName("idHomeTeam")
    var eventDetailHomeId: String? = null,

    @SerializedName("idAwayTeam")
    var eventDetailAwayId: String? = null,

    @SerializedName("strHomeGoalDetails")
    var eventDetailHomeGoal: String? = null,

    @SerializedName("strAwayGoalDetails")
    var eventDetailAwayGoal: String? = null,

    @SerializedName("strHomeRedCards")
    var eventDetailHomeRed: String? = null,

    @SerializedName("strAwayRedCards")
    var eventDetailAwayRed: String? = null,

    @SerializedName("strHomeYellowCards")
    var eventDetailHomeYellow: String? = null,

    @SerializedName("strAwayYellowCards")
    var eventDetailAwayYellow: String? = null
)