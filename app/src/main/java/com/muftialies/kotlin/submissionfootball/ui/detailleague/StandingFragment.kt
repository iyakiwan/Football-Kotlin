package com.muftialies.kotlin.submissionfootball.ui.detailleague


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.muftialies.kotlin.submissionfootball.DetailActivity
import com.muftialies.kotlin.submissionfootball.adapter.LeagueStandingAdapter
import com.muftialies.kotlin.submissionfootball.api.ApiRepository
import com.muftialies.kotlin.submissionfootball.data.LeagueStanding
import com.muftialies.kotlin.submissionfootball.mvp.leaguestanding.LeagueStandingPresenter
import com.muftialies.kotlin.submissionfootball.mvp.leaguestanding.LeagueStandingView
import com.muftialies.kotlin.submissionfootball.utils.invisible
import com.muftialies.kotlin.submissionfootball.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

/**
 * A simple [Fragment] subclass.
 */
class StandingFragment : Fragment(), LeagueStandingView {

    private var standings: MutableList<LeagueStanding> = mutableListOf()
    private lateinit var presenter: LeagueStandingPresenter
    private lateinit var adapter: LeagueStandingAdapter

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    private fun createView(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(8)
                orientation = LinearLayout.HORIZONTAL

                textView("#") {
                    textColor = Color.BLACK
                }.lparams{
                    width = dip(16)
                }

                textView("Club") {
                    textColor = Color.BLACK
                }.lparams{
                    width = dip(100)
                    leftMargin = dip(8)
                }

                textView("MP") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(24)
                    leftMargin = dip(8)
                }

                textView("W") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(16)
                    leftMargin = dip(8)
                }

                textView("D") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(16)
                    leftMargin = dip(8)
                }

                textView("L") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(16)
                    leftMargin = dip(8)
                }

                textView("GF") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(24)
                    leftMargin = dip(8)
                }

                textView("GA") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(24)
                    leftMargin = dip(8)
                }

                textView("GD") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(24)
                    leftMargin = dip(8)
                }

                textView("Pts") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = Color.BLACK
                }.lparams {
                    width = dip(24)
                    leftMargin = dip(8)
                }
            }

            relativeLayout {
                lparams(width = matchParent, height = matchParent)

                listTeam = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }

                progressBar = progressBar().lparams {
                    centerHorizontally()
                    centerVertically()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LeagueStandingAdapter(ctx,standings) {
        }

        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueStandingPresenter(this, request, gson)

        presenter.getLeagueStandings(DetailActivity.ID_LEAGUE)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueStanding(data: List<LeagueStanding>, status: Boolean) {
        if (status) {
            var rank = 0
            for (i in data){
                rank += 1
                i.standingTeamRank = rank
            }
            standings.clear()
            standings.addAll(data)
            adapter.notifyDataSetChanged()
        } else {
            val toast = Toast.makeText(context, "Standing not found", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
