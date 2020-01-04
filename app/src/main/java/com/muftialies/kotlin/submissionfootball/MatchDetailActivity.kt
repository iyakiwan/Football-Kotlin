package com.muftialies.kotlin.submissionfootball

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueDetailTeam
import com.muftialies.kotlin.submissionfootball.data.LeagueMatchDetail
import com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail.LeagueMatchDetailPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leaguematchdetail.LeagueMatchDetailView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import com.muftialies.kotlin.submissionfootball.R.menu.detail_menu_match
import com.muftialies.kotlin.submissionfootball.R.id.add_to_favorite
import com.muftialies.kotlin.submissionfootball.favorite.FavoriteMatch
import com.muftialies.kotlin.submissionfootball.favorite.databaseMatch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class MatchDetailActivity : AppCompatActivity(), LeagueMatchDetailView {

    companion object {
        const val DETAIL_EVENT_ID = "DETAIL_LEAGUE_ID"
        const val DETAIL_LEAGUE_NAME = "DETAIL_LEAGUE_NAME"
    }

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var loading: Boolean = false
    private lateinit var favoriteMatch: FavoriteMatch

    private lateinit var idEvent : String
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

        favoriteState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu_match, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (!loading){
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        loading = true
        activity_match_detail.invisible()
        progressBarLeagueMatch.visible()
    }

    override fun hideLoading() {
        activity_match_detail.visible()
        progressBarLeagueMatch.invisible()
        loading = false
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

        favoriteMatch = FavoriteMatch(0,data[0].eventDetailId,data[0].eventDetailName,data[0].eventDetailDate,data[0].eventDetailTime,data[0].eventDetailRound,
            data[0].eventDetailSeason,data[0].eventDetailHomeId,data[0].eventDetailHomeTeam,data[0].eventDetailHomeScore,data[0].eventDetailHomeGoal,
            data[0].eventDetailHomeRed,data[0].eventDetailHomeYellow,"",data[0].eventDetailAwayId,data[0].eventDetailAwayTeam,
            data[0].eventDetailAwayScore,data[0].eventDetailAwayGoal,data[0].eventDetailAwayRed,data[0].eventDetailAwayYellow,"")

        tvLeagueMatchDetailHomeScore.text = data[0].eventDetailHomeScore
        tvLeagueMatchDetailAwayScore.text = data[0].eventDetailAwayScore
        tvLeagueMatchDetailHomeTeam.text = data[0].eventDetailHomeTeam
        tvLeagueMatchDetailAwayTeam.text = data[0].eventDetailAwayTeam

        //Home
        tvHomeInfo.text = "Home info (" + data[0].eventDetailHomeTeam + ")"
        tvLeagueMatchHomeGoal.text = "Detail Goal :" + data[0].eventDetailHomeGoal
        tvLeagueMatchHomeYellow.text = "Yellow Card : " + data[0].eventDetailHomeYellow
        tvLeagueMatchHomeRed.text = "Red Card : " + data[0].eventDetailHomeRed

        //Away
        tvAwayInfo.text = "Home info (" + data[0].eventDetailAwayTeam + ")"
        tvLeagueMatchAwayGoal.text = "Detail Goal :" + data[0].eventDetailAwayGoal
        tvLeagueMatchAwayYellow.text = "Yellow Card : " + data[0].eventDetailAwayYellow
        tvLeagueMatchAwayRed.text = "Red Card : " + data[0].eventDetailAwayRed

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
        favoriteMatch.favoriteDetailHomeLogo = data1[0].teamDetailLogo
        favoriteMatch.favoriteDetailAwayLogo = data2[0].teamDetailLogo

        Picasso.get().load(data1[0].teamDetailLogo).fit().into(ivLeagueMatchDetailHomeLogo)
        Picasso.get().load(data2[0].teamDetailLogo).fit().into(ivLeagueMatchDetailAwayLogo)
    }

    private fun addToFavorite(){
        try {
            databaseMatch.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.MATCH_ID to favoriteMatch.favoriteDetailId,
                    FavoriteMatch.MATCH_NAME to favoriteMatch.favoriteDetailName,
                    FavoriteMatch.MATCH_DATE to favoriteMatch.favoriteDetailDate,
                    FavoriteMatch.MATCH_TIME to favoriteMatch.favoriteDetailTime,
                    FavoriteMatch.MATCH_ROUND to favoriteMatch.favoriteDetailRound,
                    FavoriteMatch.MATCH_SESSION to favoriteMatch.favoriteDetailSeason,
                    FavoriteMatch.MATCH_HOME_ID to favoriteMatch.favoriteDetailHomeId,
                    FavoriteMatch.MATCH_HOME_TEAM to favoriteMatch.favoriteDetailHomeTeam,
                    FavoriteMatch.MATCH_HOME_SCORE to favoriteMatch.favoriteDetailHomeScore,
                    FavoriteMatch.MATCH_HOME_GOAL to favoriteMatch.favoriteDetailHomeGoal,
                    FavoriteMatch.MATCH_HOME_RED to favoriteMatch.favoriteDetailHomeRed,
                    FavoriteMatch.MATCH_HOME_YELLOW to favoriteMatch.favoriteDetailHomeYellow,
                    FavoriteMatch.MATCH_HOME_LOGO to favoriteMatch.favoriteDetailHomeLogo,
                    FavoriteMatch.MATCH_AWAY_ID to favoriteMatch.favoriteDetailAwayId,
                    FavoriteMatch.MATCH_AWAY_TEAM to favoriteMatch.favoriteDetailAwayTeam,
                    FavoriteMatch.MATCH_AWAY_SCORE to favoriteMatch.favoriteDetailAwayScore,
                    FavoriteMatch.MATCH_AWAY_GOAL to favoriteMatch.favoriteDetailAwayGoal,
                    FavoriteMatch.MATCH_AWAY_RED to favoriteMatch.favoriteDetailAwayRed,
                    FavoriteMatch.MATCH_AWAY_YELLOW to favoriteMatch.favoriteDetailAwayYellow,
                    FavoriteMatch.MATCH_AWAY_LOGO to favoriteMatch.favoriteDetailAwayLogo)
            }

            activity_match_detail.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            activity_match_detail.snackbar(e.toString()).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            databaseMatch.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(MATCH_ID = {id})",
                    "id" to idEvent)
            }
            activity_match_detail.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            activity_match_detail.snackbar(e.toString()).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_full_white)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
    }

    private fun favoriteState(){
        databaseMatch.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs("(MATCH_ID = {id})",
                    "id" to idEvent)
            val favorites = result.parseList(classParser<FavoriteMatch>())

            if (favorites.isNotEmpty()){
                isFavorite = true
                showDetailMatchLeagueFavorite(favorites[0])
                progressBarLeagueMatch.invisible()
            } else{
                presenter.getLeagueMatchDetail(idEvent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailMatchLeagueFavorite(data: FavoriteMatch) {
        tvLeagueMatchDetailTitle.text = data.favoriteDetailName

        tvLeagueMatchDetailHomeScore.text = data.favoriteDetailHomeScore
        tvLeagueMatchDetailAwayScore.text = data.favoriteDetailAwayScore
        tvLeagueMatchDetailHomeTeam.text = data.favoriteDetailHomeTeam
        tvLeagueMatchDetailAwayTeam.text = data.favoriteDetailAwayTeam

        //Home
        tvHomeInfo.text = "Home info (" + data.favoriteDetailHomeTeam + ")"
        tvLeagueMatchHomeGoal.text = "Detail Goal :" + data.favoriteDetailHomeGoal
        tvLeagueMatchHomeYellow.text = "Yellow Card : " + data.favoriteDetailHomeYellow
        tvLeagueMatchHomeRed.text = "Red Card : " + data.favoriteDetailHomeRed

        //Away
        tvAwayInfo.text = "Home info (" + data.favoriteDetailAwayTeam + ")"
        tvLeagueMatchAwayGoal.text = "Detail Goal :" + data.favoriteDetailAwayGoal
        tvLeagueMatchAwayYellow.text = "Yellow Card : " + data.favoriteDetailAwayYellow
        tvLeagueMatchAwayRed.text = "Red Card : " + data.favoriteDetailAwayRed

        tvLeagueMatchDetailDate.text = "Date Match : " + data.favoriteDetailDate
        tvLeagueMatchDetailTime.text = "Time Match : " + data.favoriteDetailTime
        tvLeagueMatchDetailRound.text = "Round : " + data.favoriteDetailRound
        tvLeagueMatchDetailSession.text = "Session : " + data.favoriteDetailSeason


        Picasso.get().load(data.favoriteDetailHomeLogo).fit().into(ivLeagueMatchDetailHomeLogo)
        Picasso.get().load(data.favoriteDetailAwayLogo).fit().into(ivLeagueMatchDetailAwayLogo)

        favoriteMatch = data
    }
}
