package com.muftialies.kotlin.submissionfootball.ui.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.muftialies.kotlin.submissionfootball.MatchDetailActivity
import com.muftialies.kotlin.submissionfootball.R
import com.muftialies.kotlin.submissionfootball.adapter.FavoriteMatchAdapter
import com.muftialies.kotlin.submissionfootball.favorite.FavoriteMatch
import com.muftialies.kotlin.submissionfootball.favorite.databaseMatch
import com.muftialies.kotlin.submissionfootball.utils.invisible
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMatchFragment : Fragment() {

    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(ctx, favoriteMatches) {
            startActivity(
                intentFor<MatchDetailActivity>(
                    MatchDetailActivity.DETAIL_EVENT_ID to it.favoriteDetailId,
                    MatchDetailActivity.DETAIL_LEAGUE_NAME to (it.favoriteDetailHomeTeam + " vs " + it.favoriteDetailAwayTeam)
                )
            )
        }

        progressBarLeagueMatch.invisible()

        rv_match.layoutManager = LinearLayoutManager(context)
        rv_match.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite(){
        favoriteMatches.clear()
        context?.databaseMatch?.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatches.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}
