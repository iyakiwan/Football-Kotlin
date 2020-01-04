package com.muftialies.kotlin.submissionfootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.data.LeaguePlayer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player_list.view.*

class LeaguePlayerAdapter (private val context: Context, private val players: List<LeaguePlayer>, private val listener: (LeaguePlayer) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player_list, parent, false))

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }
}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(leaguePlayer: LeaguePlayer, listener: (LeaguePlayer) -> Unit) {

        itemView.tvPlayerName.text = leaguePlayer.playerName
        Picasso.get().load(leaguePlayer.playerPhoto).into(itemView.ivPlayerPhoto)

        itemView.setOnClickListener {
            listener(leaguePlayer)
        }
    }
}