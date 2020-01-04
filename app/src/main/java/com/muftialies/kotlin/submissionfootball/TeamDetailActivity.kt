package com.muftialies.kotlin.submissionfootball

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.favorite.FavoriteTeam
import com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail.LeagueTeamDetailPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leagueteamdetail.LeagueTeamDetailView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import com.muftialies.kotlin.submissionfootball.R.id.add_to_favorite
import com.muftialies.kotlin.submissionfootball.adapter.LeaguePlayerAdapter
import com.muftialies.kotlin.submissionfootball.data.LeaguePlayer
import com.muftialies.kotlin.submissionfootball.favorite.databaseTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TeamDetailActivity : AppCompatActivity(), LeagueTeamDetailView {

    companion object {
        const val DETAIL_TEAM_ID = "DETAIL_TEAM_ID"
        const val DETAIL_TEAM_NAME = "DETAIL_TEAM_NAME"

    }

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView
    private lateinit var teamKeyPlayer: TextView
    private lateinit var scrollView: ScrollView

    private lateinit var progressBar: ProgressBar
    private lateinit var listPlayer: RecyclerView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var loading: Boolean = false
    private lateinit var favoriteTeam: FavoriteTeam

    private lateinit var  idTeam : String
    private lateinit var  nameTeam : String

    private lateinit var presenter: LeagueTeamDetailPresenter

    private var player: MutableList<LeaguePlayer> = mutableListOf()
    private lateinit var adapter: LeaguePlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        scrollView = scrollView {
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

                    teamKeyPlayer = textView ("List Players") {
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{topMargin = dip(10)}

                    listPlayer = recyclerView {
                        lparams(width = matchParent, height = dip(155))
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
                    }.lparams{topMargin = dip(5)}

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
        idTeam = intent.getStringExtra(DETAIL_TEAM_ID)
        nameTeam = intent.getStringExtra(DETAIL_TEAM_NAME)
        supportActionBar?.title = nameTeam

        adapter = LeaguePlayerAdapter(this, player) {
            /*startActivity(
                intentFor<MatchDetailActivity>(
                    MatchDetailActivity.DETAIL_EVENT_ID to it.eventId,
                    MatchDetailActivity.DETAIL_LEAGUE_NAME to (it.eventHomeTeam + " vs " + it.eventAwayTeam)
                )
            )*/
        }

        listPlayer.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueTeamDetailPresenter(this, request, gson)
//        presenter.getLeagueDetailTeams(ID_TEAM)

        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu_match, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            add_to_favorite -> {
                if (!loading){
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                }
            }
        }
        return true
    }

    override fun showLoading() {
        progressBar.visible()
        teamKeyPlayer.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        teamKeyPlayer.visible()
    }

    override fun showLeagueDetailTeam(
        data: List<LeagueDetailTeam>,
        status: Boolean
    ) {

        favoriteTeam = FavoriteTeam(0,data[0].teamDetailId,data[0].teamDetailName,
            data[0].teamDetailLogo,data[0].teamDetailFormedYear,data[0].teamDetailStadium,data[0].teamDetailDescription)

        Picasso.get().load(data[0].teamDetailLogo).into(teamBadge)
        teamName.text = data[0].teamDetailName
        teamDescription.text = data[0].teamDetailDescription
        teamFormedYear.text = data[0].teamDetailFormedYear
        teamStadium.text = data[0].teamDetailStadium
    }

    override fun showLeagueDetailPlayer(data: List<LeaguePlayer>, status: Boolean) {
        val value: List<LeaguePlayer> = data.filter { it.teamId == idTeam }

        if (status && value.isNotEmpty()) {
            player.clear()
            player.addAll(value)
            adapter.notifyDataSetChanged()
        } else {
            val toast = Toast.makeText(this, this.resources?.getString(R.string.alertSearch), Toast.LENGTH_SHORT)
            toast.show()
            player.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun addToFavorite(){
        try {
            databaseTeam.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to favoriteTeam.favoriteTeamId,
                    FavoriteTeam.TEAM_NAME to favoriteTeam.favoriteTeamName,
                    FavoriteTeam.TEAM_LOGO to favoriteTeam.favoriteTeamLogo,
                    FavoriteTeam.TEAM_YEAR to favoriteTeam.favoriteTeamYear,
                    FavoriteTeam.TEAM_STADIUM to favoriteTeam.favoriteTeamStadium,
                    FavoriteTeam.TEAM_DESC to favoriteTeam.favoriteTeamDesc)
            }

            scrollView.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            scrollView.snackbar(e.toString()).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            databaseTeam.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to idTeam)
            }
            scrollView.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            scrollView.snackbar(e.toString()).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_full_white)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
    }

    private fun favoriteState(){
        databaseTeam.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to idTeam)
            val favorites = result.parseList(classParser<FavoriteTeam>())

            if (favorites.isNotEmpty()){
                isFavorite = true
                showDetailMatchLeagueFavorite(favorites[0])
                presenter.getLeagueDetailPlayer(nameTeam)
            } else{
                presenter.getLeagueDetailTeams(idTeam)
                presenter.getLeagueDetailPlayer(nameTeam)
            }
        }
    }

    private fun showDetailMatchLeagueFavorite(data: FavoriteTeam) {

        Picasso.get().load(data.favoriteTeamLogo).into(teamBadge)
        teamName.text = data.favoriteTeamName
        teamDescription.text = data.favoriteTeamDesc
        teamFormedYear.text = data.favoriteTeamYear
        teamStadium.text = data.favoriteTeamStadium

        favoriteTeam = data
    }
}
