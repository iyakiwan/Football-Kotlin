package com.muftialies.kotlin.submissionfootball.ui.detailleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.DetailActivity
import com.muftialies.kotlin.submissionfootball.MatchDetailActivity
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.adapter.LeagueMatchAdapter
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueMatch
import com.muftialies.kotlin.submissionfootball.mvp.leaguematch.LeagueMatchPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leaguematch.LeagueMatchView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor

/**
 * A simple [Fragment] subclass.
 */
class PastMatchFragment : Fragment(), LeagueMatchView {

    private var match: MutableList<LeagueMatch> = mutableListOf()
    private lateinit var presenter: LeagueMatchPresenter
    private lateinit var adapter: LeagueMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LeagueMatchAdapter(ctx, match) {
            /*val toast = Toast.makeText(ctx, it.eventId, Toast.LENGTH_SHORT)
            toast.show()*/
            startActivity(
                intentFor<MatchDetailActivity>(
                    MatchDetailActivity.DETAIL_EVENT_ID to it.eventId,
                    MatchDetailActivity.DETAIL_LEAGUE_NAME to (it.eventHomeTeam + " vs " + it.eventAwayTeam)
                )
            )
        }

        rv_match.layoutManager = LinearLayoutManager(ctx)
        rv_match.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueMatchPresenter(this, request, gson)

        presenter.getLeagueMatchPast(DetailActivity.ID_LEAGUE)
    }

    override fun showLoading() {
        progressBarLeagueMatch.visible()
    }

    override fun hideLoading() {
        progressBarLeagueMatch.invisible()
    }

    override fun showDetailMatch(data: List<LeagueMatch>, status: Boolean) {
        if (status) {
            match.clear()
            match.addAll(data)
            adapter.notifyDataSetChanged()
        } else {
            val toast = Toast.makeText(ctx, "Past Match not found", Toast.LENGTH_SHORT)
            toast.show()
        }
    }


}
