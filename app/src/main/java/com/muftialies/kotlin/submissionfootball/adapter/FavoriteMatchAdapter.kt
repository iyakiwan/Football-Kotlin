package com.muftialies.kotlin.submissionfootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.favorite.Favorite
import kotlinx.android.synthetic.main.item_match_list.view.*

class FavoriteMatchAdapter (private val context: Context, private val events: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_list, parent, false))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
    override fun getItemCount(): Int = events.size
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {

        itemView.tvLeagueMatchDate.text = favorite.favoriteDetailDate
        itemView.tvLeagueMatchHomeTeam.text = favorite.favoriteDetailHomeTeam
        itemView.tvLeagueMatchHomeScore.text = favorite.favoriteDetailHomeScore
        itemView.tvLeagueMatchAwayTeam.text = favorite.favoriteDetailAwayTeam
        itemView.tvLeagueMatchAwayScore.text = favorite.favoriteDetailAwayScore

        itemView.setOnClickListener {
            listener(favorite)
        }
    }
}