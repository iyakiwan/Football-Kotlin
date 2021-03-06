package com.muftialies.kotlin.submissionfootball

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.adapter.LeagueMatchAdapter
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueMatch
import com.muftialies.kotlin.submissionfootball.mvp.leaguematch.LeagueMatchPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leaguematch.LeagueMatchView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchMatchActivity : AppCompatActivity(), LeagueMatchView {

    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var etQuerySearch: EditText
    private lateinit var btSearch: Button

    private var results: MutableList<LeagueMatch> = mutableListOf()
    private lateinit var presenter: LeagueMatchPresenter
    private lateinit var adapter: LeagueMatchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER_HORIZONTAL

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)

                etQuerySearch = editText{
                    id = R.id.etSearchEvent
                    lines = 1
                    imeOptions = EditorInfo.IME_ACTION_SEARCH
                    hint = "Search in the here"
                }.lparams(width = dip(250), height = wrapContent)

                btSearch = button("Search"){
                    id = R.id.btSearchEvent
                }
            }
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                listEvent = recyclerView {
                    id = R.id.rvSearchEvent
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }

                progressBar = progressBar {
                    id = R.id.pbSearchEvent
                }.lparams {
                    centerHorizontally()
                    centerVertically()
                }
            }
        }

        progressBar.invisible()
        adapter = LeagueMatchAdapter(this, results) {
            startActivity(
                intentFor<MatchDetailActivity>(
                    MatchDetailActivity.DETAIL_EVENT_ID to it.eventId,
                    MatchDetailActivity.DETAIL_LEAGUE_NAME to (it.eventHomeTeam + " vs " + it.eventAwayTeam)
                )
            )
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueMatchPresenter(this, request, gson)

        supportActionBar?.title = resources.getString(R.string.title_search_activity)

        btSearch.setOnClickListener {
            if(etQuerySearch.text.isNullOrEmpty()){
                val toast = Toast.makeText(this, this.resources?.getString(R.string.alertEmptySearch), Toast.LENGTH_SHORT)
                toast.show()
            } else {
                presenter.getLeagueMatchSearchResults(etQuerySearch.text.toString())
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailMatch(data: List<LeagueMatch>, status: Boolean) {
        if (status) {
            val value: List<LeagueMatch> = data.filter { it.eventSport == "Soccer" }

            results.clear()
            results.addAll(value)
            adapter.notifyDataSetChanged()
        } else {
            val toast = Toast.makeText(this, this.resources?.getString(R.string.alertSearch), Toast.LENGTH_SHORT)
            toast.show()
            results.clear()
            adapter.notifyDataSetChanged()
        }
    }
}
