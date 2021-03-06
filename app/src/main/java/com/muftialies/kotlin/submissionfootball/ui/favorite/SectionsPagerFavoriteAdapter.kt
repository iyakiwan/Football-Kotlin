package com.muftialies.kotlin.submissionfootball.ui.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.muftialies.kotlin.submissionfootball.R

@Suppress("DEPRECATION")
class SectionsPagerFavoriteAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf(
        R.string.tab_text_5,
        R.string.tab_text_3
    )

    private val pages = listOf(
        FavoriteMatchFragment(),
        FavoriteTeamFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return pages.size
    }
}