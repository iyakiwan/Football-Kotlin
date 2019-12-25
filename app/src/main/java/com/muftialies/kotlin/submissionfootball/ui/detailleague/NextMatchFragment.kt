package com.muftialies.kotlin.submissionfootball.ui.detailleague

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.DetailActivity
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

/**
 * A placeholder fragment containing a simple view.
 */
class NextMatchFragment : Fragment(), LeagueMatchView {

    private var matchs: MutableList<LeagueMatch> = mutableListOf()
    private lateinit var presenter: LeagueMatchPresenter
    private lateinit var adapter: LeagueMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LeagueMatchAdapter(ctx, matchs){
            val toast = Toast.makeText(ctx, it.eventId, Toast.LENGTH_SHORT)
            toast.show()
        }

        rv_match.layoutManager = LinearLayoutManager(ctx)
        rv_match.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueMatchPresenter(this, request, gson)

        presenter.getLeagueMatchNext(DetailActivity.ID_LEAGUE)
    }

    override fun showLoading() {
        progressBarLeagueMatch.visible()
    }

    override fun hideLoading() {
        progressBarLeagueMatch.invisible()
    }

    override fun showDetailMatch(data: List<LeagueMatch>) {
        matchs.clear()
        matchs.addAll(data)
        adapter.notifyDataSetChanged()

    }

}