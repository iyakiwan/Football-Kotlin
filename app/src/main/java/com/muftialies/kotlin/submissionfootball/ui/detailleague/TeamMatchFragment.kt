package com.muftialies.kotlin.submissionfootball.ui.detailleague

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.DetailActivity
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.adapter.LeagueTeamsAdapter
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueTeam
import com.muftialies.kotlin.submissionfootball.mvp.leagueteam.LeagueTeamPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leagueteam.LeagueTeamView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class TeamMatchFragment : Fragment(), LeagueTeamView{
    private var teams: MutableList<LeagueTeam> = mutableListOf()
    private lateinit var presenter: LeagueTeamPresenter
    private lateinit var adapter: LeagueTeamsAdapter

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

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

            progressBar = progressBar {
            }.lparams {
                centerHorizontally()
                centerVertically()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LeagueTeamsAdapter(teams) {
            /*startActivity(
                intentFor<MatchDetailActivity>(
                    MatchDetailActivity.DETAIL_EVENT_ID to it.eventId,
                    MatchDetailActivity.DETAIL_LEAGUE_NAME to (it.eventHomeTeam + " vs " + it.eventAwayTeam)
                )
            )*/
        }

        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueTeamPresenter(this, request, gson)

        if(savedInstanceState == null){
            presenter.getLeagueTeams(DetailActivity.ID_LEAGUE)
        }

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueTeam(data: List<LeagueTeam>, status: Boolean) {
        if (status) {
            teams.clear()
            teams.addAll(data)
            adapter.notifyDataSetChanged()
        } else {
            val toast = Toast.makeText(context, "Team not found", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}