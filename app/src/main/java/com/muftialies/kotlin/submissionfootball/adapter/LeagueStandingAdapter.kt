package com.muftialies.kotlin.submissionfootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.data.LeagueStanding
import kotlinx.android.synthetic.main.item_standing_list.view.*

class LeagueStandingAdapter (private val context: Context, private val standings: List<LeagueStanding>, private val listener: (LeagueStanding) -> Unit)
    : RecyclerView.Adapter<StandingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StandingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_standing_list, parent, false))

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bindItem(standings[position], listener)
    }
    override fun getItemCount(): Int = standings.size
}

class StandingViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(standing: LeagueStanding, listener: (LeagueStanding) -> Unit) {
        itemView.etStandingTeamRank.text = standing.standingTeamRank.toString()
        itemView.etStandingTeamName.text = standing.standingTeamName
        itemView.etStandingTeamPlayed.text = standing.standingTeamPlayed
        itemView.etStandingTeamWin.text = standing.standingTeamTotalWin
        itemView.etStandingTeamDraw.text = standing.standingTeamTotalDraw
        itemView.etStandingTeamLose.text = standing.standingTeamTotalLose
        itemView.etStandingTeamGoalFor.text = standing.standingTeamGoalFor
        itemView.etStandingTeamGoalAgainst.text = standing.standingTeamGoalAgainst
        itemView.etStandingTeamGoalDifference.text = standing.standingTeamGoalDifference
        itemView.etStandingTeamTotalPoint.text = standing.standingTeamTotalPoint


        itemView.setOnClickListener {
            listener(standing)
        }
    }
}