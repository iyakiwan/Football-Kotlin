package com.muftialies.kotlin.submissionfootball.ui.favorite


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muftialies.kotlin.submissionfootball.TeamDetailActivity
import com.muftialies.kotlin.submissionfootball.adapter.FavoriteTeamAdapter
import com.muftialies.kotlin.submissionfootball.favorite.FavoriteTeam
import com.muftialies.kotlin.submissionfootball.favorite.databaseTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.intentFor

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamFragment : Fragment() {

    private var favoriteTeams: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter

    private lateinit var listTeam: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    private fun createView(ui: AnkoContext<Context>): View = with(ui){
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)

            listTeam = recyclerView {
                lparams(width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteTeamAdapter(favoriteTeams) {
            startActivity(
                intentFor<TeamDetailActivity>(
                    TeamDetailActivity.DETAIL_TEAM_ID to it.favoriteTeamId,
                    TeamDetailActivity.DETAIL_TEAM_NAME to it.favoriteTeamName
                )
            )
        }

        listTeam.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite(){
        favoriteTeams.clear()
        context?.databaseTeam?.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteTeams.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
