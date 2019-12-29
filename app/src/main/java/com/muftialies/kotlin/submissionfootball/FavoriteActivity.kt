package com.muftialies.kotlin.submissionfootball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.muftialies.kotlin.submissionfootball.adapter.FavoriteMatchAdapter
import com.muftialies.kotlin.submissionfootball.favorite.Favorite
import com.muftialies.kotlin.submissionfootball.favorite.database
import com.muftialies.kotlin.submissionfootball.utils.invisible
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.fragment_match.progressBarLeagueMatch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.intentFor

class FavoriteActivity : AppCompatActivity() {
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_match)

        supportActionBar?.title = resources.getString(R.string.title_favorite_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteMatchAdapter(this, favorites) {
            startActivity(
                intentFor<MatchDetailActivity>(
                    MatchDetailActivity.DETAIL_EVENT_ID to it.favoriteDetailId,
                    MatchDetailActivity.DETAIL_LEAGUE_NAME to (it.favoriteDetailHomeTeam + " vs " + it.favoriteDetailAwayTeam)
                )
            )
        }

        progressBarLeagueMatch.invisible()

        rv_match.layoutManager = LinearLayoutManager(this)
        rv_match.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite(){
        favorites.clear()
        this.database.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
