package com.muftialies.kotlin.submissionfootball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.adapter.LeagueTeamsAdapter
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueTeam
import com.muftialies.kotlin.submissionfootball.mvp.leagueteam.LeagueTeamPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leagueteam.LeagueTeamView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchTeamActivity : AppCompatActivity(), LeagueTeamView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var etQuerySearch: EditText
    private lateinit var btSearch: Button

    private var results: MutableList<LeagueTeam> = mutableListOf()
    private lateinit var presenter: LeagueTeamPresenter
    private lateinit var adapter: LeagueTeamsAdapter

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

                listTeam = recyclerView {
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
        adapter = LeagueTeamsAdapter(results) {
            startActivity<TeamDetailActivity>(
                TeamDetailActivity.DETAIL_TEAM_ID to it.teamId,
                TeamDetailActivity.DETAIL_TEAM_NAME to it.teamName
            )
        }

        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueTeamPresenter(this, request, gson)

        supportActionBar?.title = resources.getString(R.string.title_search_activity_2)

        btSearch.setOnClickListener {
            if(etQuerySearch.text.isNullOrEmpty()){
                val toast = Toast.makeText(this, this.resources?.getString(R.string.alertEmptySearch), Toast.LENGTH_SHORT)
                toast.show()
            } else {
                presenter.getLeagueSearchTeams(etQuerySearch.text.toString())
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

    override fun showLeagueTeam(data: List<LeagueTeam>, status: Boolean) {
        val value: List<LeagueTeam> = data.filter { it.teamSport == "Soccer" }
        if (status && value.isNotEmpty()) {
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
