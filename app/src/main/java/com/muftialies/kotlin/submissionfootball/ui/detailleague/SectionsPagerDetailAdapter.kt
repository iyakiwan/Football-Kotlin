package com.muftialies.kotlin.submissionfootball.ui.detailleague

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.muftialies.kotlin.submissionfootball.R

@Suppress("DEPRECATION")
class SectionsPagerDetailAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf(
        R.string.tab_text_4,
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3
    )

    private val pages = listOf(
        StandingFragment(),
        PastMatchFragment(),
        NextMatchFragment(),
        TeamMatchFragment()
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