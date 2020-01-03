package com.muftialies.kotlin.submissionfootball.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.data.LeagueTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class LeagueTeamsAdapter(private val itemTeams: List<LeagueTeam>, private val listener: (LeagueTeam) -> Unit) :
    RecyclerView.Adapter<LeagueTeamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        ItemList().createView(AnkoContext.create(parent.context, parent))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemTeams[position], listener)
    }

    override fun getItemCount(): Int = itemTeams.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTeam = view.findViewById<TextView>(R.id.tvLeagueNameId)
        private val imageTeam = view.findViewById<ImageView>(R.id.tvLeagueImageId)

        fun bindItem(items: LeagueTeam, listener: (LeagueTeam) -> Unit) {
            nameTeam.text = items.teamName
            items.teamBadge?.let { Picasso.get().load(it).fit().into(imageTeam) }
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }
}