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

class SearchActivity : AppCompatActivity(), LeagueMatchView {

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
                    imeOptions = EditorInfo.IME_ACTION_NEXT
                }.lparams(width = dip(250), height = wrapContent)

                btSearch = button("Search")
            }
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                listEvent = recyclerView {
                    lparams(width = matchParent, height = matchParent)
                    layoutManager = LinearLayoutManager(context)
                }

                progressBar = progressBar {
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

        supportActionBar?.title = "Search Event Soccer"

        btSearch.setOnClickListener {
            presenter.getLeagueMatchSearchResults(etQuerySearch.text.toString())
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
        }
    }
}
