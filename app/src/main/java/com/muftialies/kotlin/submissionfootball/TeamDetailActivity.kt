package com.muftialies.kotlin.submissionfootball

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail.LeagueTeamDetailPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail.LeagueTeamDetailView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TeamDetailActivity : AppCompatActivity(), LeagueTeamDetailView {

    companion object {
        const val DETAIL_TEAM_ID = "DETAIL_TEAM_ID"
        const val DETAIL_TEAM_NAME = "DETAIL_TEAM_NAME"
        var ID_TEAM = ""
    }

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var progressBar: ProgressBar
    private lateinit var listPlayer: RecyclerView

    private lateinit var presenter: LeagueTeamDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        scrollView {
            isVerticalScrollBarEnabled = false
            relativeLayout {
                lparams(width = matchParent, height = matchParent)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(10)
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    teamBadge = imageView {}.lparams(height = dip(75))

                    teamName = textView {
                        textSize = 24f
                        typeface = Typeface.DEFAULT_BOLD
                        textColor = ContextCompat.getColor(context, R.color.colorAccent)
                    }.lparams {
                        topMargin = dip(5)
                    }

                    teamFormedYear = textView {
                        this.gravity = Gravity.CENTER
                    }

                    teamStadium = textView {
                        this.gravity = Gravity.CENTER
                    }

                    textView ("List Players") {
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{topMargin = dip(10)}

                    listPlayer = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
                    }

                    teamDescription = textView().lparams {
                        topMargin = dip(20)
                    }
                }

                progressBar = progressBar ()
                    .lparams {
                    centerHorizontally()
                    centerVertically()
                }
            }
        }

        val intent = intent
        ID_TEAM = intent.getStringExtra(DETAIL_TEAM_ID)
        supportActionBar?.title = intent.getStringExtra(DETAIL_TEAM_NAME)

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueTeamDetailPresenter(this, request, gson)
        presenter.getLeagueDetailTeams(ID_TEAM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueDetailTeam(
        data1: List<LeagueDetailTeam>,
        data2: List<LeagueDetailTeam>,
        status: Boolean
    ) {

        Picasso.get().load(data1[0].teamDetailLogo).into(teamBadge)
        teamName.text = data1[0].teamDetailName
        teamDescription.text = data1[0].teamDetailDescription
        teamFormedYear.text = data1[0].teamDetailFormedYear
        teamStadium.text = data1[0].teamDetailStadium
    }
}
