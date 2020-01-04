package com.muftialies.kotlin.submissionfootball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.muftialies.kotlin.submissionfootball.ui.favorite.SectionsPagerFavoriteAdapter

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = resources.getString(R.string.title_favorite_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerFavoriteAdapter = SectionsPagerFavoriteAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerFavoriteAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

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
