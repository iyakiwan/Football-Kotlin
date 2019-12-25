package com.muftialies.kotlin.submissionfootball

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueDetail
import com.muftialies.kotlin.submissionfootball.mvp.leaguedetail.LeagueDetailPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leaguedetail.LeagueDetailView
import com.muftialies.kotlin.submissionfootball.ui.detailleague.SectionsPagerAdapter
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity(), LeagueDetailView{

    companion object {
        const val DETAIL_LEAGUE_ID = "DETAIL_LEAGUE_ID"
        const val DETAIL_LEAGUE_NAME = "DETAIL_LEAGUE_NAME"
        var ID_LEAGUE =""
    }

    var strLeague ="Wait, Ok"
    private lateinit var presenterLeagueDetail: LeagueDetailPresenter
    /*private lateinit var pbDetailLeague: ProgressBar
    private lateinit var ivLeagueDetail: ImageView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /*ivLeagueDetail = findViewById(R.id.imgLeagueDetail)
        pbDetailLeague = findViewById(R.id.progressBarDetailLeague)
        val tvTitleBar = findViewById<TextView>(R.id.title)*/

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val intent = intent
        ID_LEAGUE = intent.getStringExtra(DETAIL_LEAGUE_ID)
        supportActionBar?.title = intent.getStringExtra(DETAIL_LEAGUE_NAME)

//        tvTitleBar.text = intent.getStringExtra(DETAIL_LEAGUE_NAME)

        val request = ApiRepository()
        val gson = Gson()
        presenterLeagueDetail = LeagueDetailPresenter(this, request, gson)

        presenterLeagueDetail.getLeagueDetail(ID_LEAGUE)

       /* ivLeagueDetail.setOnClickListener {
            alert(strLeague, "Info Detail League") {
                okButton {  }
            }.show()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDetailInfoLeague -> {
                alert(strLeague, "Info Detail League") {
                    okButton { }
                }.show()

            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun showLoading() {
        /*pbDetailLeague.visible()
        ivLeagueDetail.invisible()*/
    }

    override fun hideLoading() {
        /*pbDetailLeague.invisible()
        ivLeagueDetail.visible()*/
    }

    override fun showDetailLeague(data: List<LeagueDetail>) {
        strLeague = "Id League : ${data[0].leagueId} \n" +
                "Name League : ${data[0].leagueName} \n" +
                "Country League : ${data[0].leagueCountry} \n" +
                "Sporty League : ${data[0].leagueSport}"
    }

}