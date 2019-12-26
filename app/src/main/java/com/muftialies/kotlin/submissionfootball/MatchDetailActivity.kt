package com.muftialies.kotlin.submissionfootball

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchDetail
import com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail.LeagueMatchDetailPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail.LeagueMatchDetailView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*

class MatchDetailActivity : AppCompatActivity(), LeagueMatchDetailView {

    companion object {
        const val DETAIL_EVENT_ID = "DETAIL_LEAGUE_ID"
        const val DETAIL_LEAGUE_NAME = "DETAIL_LEAGUE_NAME"
    }

    var idEvent = ""
    private lateinit var presenter: LeagueMatchDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        val intent = intent
        idEvent = intent.getStringExtra(DETAIL_EVENT_ID)
        supportActionBar?.title = intent.getStringExtra(DETAIL_LEAGUE_NAME)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueMatchDetailPresenter(this, request, gson)

        presenter.getLeagueMatchDetail(idEvent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        activity_match_detail.invisible()
        progressBarLeagueMatch.visible()
    }

    override fun hideLoading() {
        activity_match_detail.visible()
        progressBarLeagueMatch.invisible()
    }

    @SuppressLint("SetTextI18n")
    override fun showDetailMatchLeague(data: List<LeagueMatchDetail>) {
        tvLeagueMatchDetailTitle.text = data[0].eventDetailName
        if (data[0].eventDetailHomeScore.isNullOrEmpty()){
            data[0].eventDetailHomeScore = "-"
        }
        if (data[0].eventDetailAwayScore.isNullOrEmpty()){
            data[0].eventDetailAwayScore = "-"
        }
        tvLeagueMatchDetailHomeScore.text = data[0].eventDetailHomeScore
        tvLeagueMatchDetailAwayScore.text = data[0].eventDetailAwayScore
        tvLeagueMatchDetailHomeTeam.text = data[0].eventDetailHomeTeam
        tvLeagueMatchDetailAwayTeam.text = data[0].eventDetailAwayTeam
        tvLeagueMatchDetailDate.text = "Date Match : " + data[0].eventDetailDate
        tvLeagueMatchDetailTime.text = "Time Match : " + data[0].eventDetailTime
        tvLeagueMatchDetailRound.text = "Round : " + data[0].eventDetailRound
        tvLeagueMatchDetailSession.text = "Session : " + data[0].eventDetailSeason
        presenter.getLeagueMatchTeam(data[0].eventDetailHomeId, data[0].eventDetailAwayId)
    }


    override fun showDetailTeamLeague(
        data1: List<LeagueDetailTeam>,
        data2: List<LeagueDetailTeam>
    ) {
        Picasso.get().load(data1[0].teamDetailLogo).fit().into(ivLeagueMatchDetailHomeLogo)
        Picasso.get().load(data2[0].teamDetailLogo).fit().into(ivLeagueMatchDetailAwayLogo)
    }
}
