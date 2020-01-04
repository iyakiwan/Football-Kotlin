package com.muftialies.kotlin.submissionfootball.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.favorite.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext

class FavoriteTeamAdapter(private val teams: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit) :
    RecyclerView.Adapter<FavoriteTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= FavoriteTeamViewHolder (
        ItemList().createView(AnkoContext.create(parent.context, parent))
    )

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}

class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTeam = view.findViewById<TextView>(R.id.tvLeagueNameId)
    private val imageTeam = view.findViewById<ImageView>(R.id.tvLeagueImageId)

    fun bindItem(items: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        nameTeam.text = items.favoriteTeamName
        items.favoriteTeamLogo?.let { Picasso.get().load(it).fit().into(imageTeam)}

        itemView.setOnClickListener {
            listener(items)
        }
    }
}