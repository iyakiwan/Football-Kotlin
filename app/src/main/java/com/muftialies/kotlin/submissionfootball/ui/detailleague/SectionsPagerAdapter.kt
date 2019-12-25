package com.muftialies.kotlin.submissionfootball.ui.detailleague

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.muftialies.kotlin.submissionfootball.R

@Suppress("DEPRECATION")
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    private val pages = listOf<Fragment>(
        PastMatchFragment(),
        NextMatchFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return pages.size
    }
}