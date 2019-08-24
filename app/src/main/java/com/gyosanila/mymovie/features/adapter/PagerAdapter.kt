package com.gyosanila.mymovie.features.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gyosanila.mymovie.features.domain.network.Pager

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:40
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class PagerAdapter(
    private val pager: List<Pager>, fm: FragmentManager
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return pager[position].fragment
    }

    override fun getCount(): Int {
        return pager.size
    }

    override fun getPageTitle(position: Int): CharSequence? = pager[position].title
}