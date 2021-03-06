package com.muftialies.kotlin.submissionfootball

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muftialies.kotlin.submissionfootball.adapter.LeagueAdapter
import com.muftialies.kotlin.submissionfootball.data.League
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    private var items: MutableList<League> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalLayout {

            recyclerView {
                lparams(width = matchParent, height = matchParent)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = LeagueAdapter(items) {

                    /*val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
                    toast.show()*/
                    startActivity<DetailActivity>(DetailActivity.DETAIL_LEAGUE_ID to it.id, DetailActivity.DETAIL_LEAGUE_NAME to it.name)
                }
            }
        }

        initData()
    }

    private fun initData() {
        val name = resources.getStringArray(R.array.league_name)
        val image = resources.obtainTypedArray(R.array.league_image)
        val id = resources.getStringArray(R.array.league_id)

        items.clear()
        for (i in name.indices) {
            items.add(
                League(
                    name[i], id[i], image.getResourceId(i, 0)
                )
            )
        }

        image.recycle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDetailSearch -> {
                lateinit var dialog: DialogInterface
                dialog = alert("What you want search?", "Mode Search") {
                    customView {
                        linearLayout {
                            padding = dip(8)
                            orientation = LinearLayout.VERTICAL

                            button("Match"){
                                setOnClickListener {
                                    dialog.dismiss()
                                    startActivity<SearchMatchActivity>()
                                }
                            }
                            button("Team"){
                                setOnClickListener {
                                    dialog.dismiss()
                                    startActivity<SearchTeamActivity>()
                                }
                            }

                        }
                    }
                }.show()
            }
            R.id.menuMatchFavorite -> {
                startActivity<FavoriteActivity>()
            }
        }
        return true
    }
}
