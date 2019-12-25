package com.muftialies.kotlin.submissionfootball.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.data.League
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class LeagueAdapter(private val itemLeagues: List<League>, private val listener: (League) -> Unit) :
    RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    class ItemList : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)

                imageView {
                    id = R.id.tvLeagueImageId
                }.lparams(width = dip(50), height = dip(50))

                textView {
                    id = R.id.tvLeagueNameId
                }.lparams(width = wrapContent, height = wrapContent) {
                    margin = dip(10)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemList().createView(
            AnkoContext.create(parent.context, parent)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemLeagues[position], listener)
    }

    override fun getItemCount(): Int = itemLeagues.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameLeague = view.findViewById<TextView>(R.id.tvLeagueNameId)
        private val imageLeague = view.findViewById<ImageView>(R.id.tvLeagueImageId)

        fun bindItem(items: League, listener: (League) -> Unit) {
            nameLeague.text = items.name
            items.image?.let { Picasso.get().load(it).fit().into(imageLeague) }
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }
}