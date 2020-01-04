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
    private lateinit var scrollView: ScrollView

    private lateinit var progressBar: ProgressBar
    private lateinit var listPlayer: RecyclerView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var loading: Boolean = false
    private lateinit var favoriteTeam: FavoriteTeam

    private lateinit var  ID_TEAM : String
    private lateinit var presenter: LeagueTeamDetailPresenter

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
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueDetailTeam(
        data1: List<LeagueDetailTeam>,
        data2: List<LeagueDetailTeam>,
        status: Boolean
    ) {

        favoriteTeam = FavoriteTeam(0,data1[0].teamDetailId,data1[0].teamDetailName,
            data1[0].teamDetailLogo,data1[0].teamDetailFormedYear,data1[0].teamDetailStadium,data1[0].teamDetailDescription)

        Picasso.get().load(data1[0].teamDetailLogo).into(teamBadge)
        teamName.text = data1[0].teamDetailName
        teamDescription.text = data1[0].teamDetailDescription
        teamFormedYear.text = data1[0].teamDetailFormedYear
        teamStadium.text = data1[0].teamDetailStadium
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
                    "id" to ID_TEAM)
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
                    "id" to ID_TEAM)
            val favorites = result.parseList(classParser<FavoriteTeam>())

            if (favorites.isNotEmpty()){
                isFavorite = true
                showDetailMatchLeagueFavorite(favorites[0])
                progressBar.invisible()
            } else{
                presenter.getLeagueDetailTeams(ID_TEAM)
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
