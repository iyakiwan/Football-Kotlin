package com.muftialies.kotlin.submissionfootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.data.LeagueMatch
import kotlinx.android.synthetic.main.item_match_list.view.*

class LeagueMatchAdapter (private val context: Context, private val events: List<LeagueMatch>, private val listener: (LeagueMatch) -> Unit)
    : RecyclerView.Adapter<MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_list, parent, false))

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
    override fun getItemCount(): Int = events.size
}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(event: LeagueMatch, listener: (LeagueMatch) -> Unit) {

        if (event.eventHomeScore.isNullOrEmpty()){
            event.eventHomeScore = "-"
        }
        if (event.eventAwayScore.isNullOrEmpty()){
            event.eventAwayScore = "-"
        }

        itemView.tvLeagueMatchDate.text = event.eventDate
        itemView.tvLeagueMatchHomeTeam.text = event.eventHomeTeam
        itemView.tvLeagueMatchHomeScore.text = event.eventHomeScore
        itemView.tvLeagueMatchAwayTeam.text = event.eventawayTeam
        itemView.tvLeagueMatchAwayScore.text = event.eventAwayScore

        itemView.setOnClickListener {
            listener(event)
        }
    }
}