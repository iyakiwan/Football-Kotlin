package com.muftialies.kotlin.submissionfootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.favorite.FavoriteMatch
import kotlinx.android.synthetic.main.item_match_list.view.*

class FavoriteMatchAdapter (private val context: Context, private val events: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_list, parent, false))

    override fun onBindViewHolder(holderMatch: FavoriteMatchViewHolder, position: Int) {
        holderMatch.bindItem(events[position], listener)
    }
    override fun getItemCount(): Int = events.size
}

class FavoriteMatchViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {

        itemView.tvLeagueMatchDate.text = favoriteMatch.favoriteDetailDate
        itemView.tvLeagueMatchHomeTeam.text = favoriteMatch.favoriteDetailHomeTeam
        itemView.tvLeagueMatchHomeScore.text = favoriteMatch.favoriteDetailHomeScore
        itemView.tvLeagueMatchAwayTeam.text = favoriteMatch.favoriteDetailAwayTeam
        itemView.tvLeagueMatchAwayScore.text = favoriteMatch.favoriteDetailAwayScore

        itemView.setOnClickListener {
            listener(favoriteMatch)
        }
    }
}