package com.gyosanila.mymovie.features.dashboard

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.fragmentMovie.FragmentMovie
import com.gyosanila.mymovie.features.fragmentTvShow.FragmentTvShow

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:40
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class DashboardPagerAdapter(
    private val context: Context, fm: FragmentManager
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        FragmentMovie(),
        FragmentTvShow()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.getString(R.string.text_title_movie)
            else -> context.getString(R.string.text_title_tv_show)
        }
    }
}