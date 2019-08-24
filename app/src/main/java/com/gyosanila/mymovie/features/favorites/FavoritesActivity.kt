package com.gyosanila.mymovie.features.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.adapter.PagerAdapter
import com.gyosanila.mymovie.features.domain.network.Pager
import com.gyosanila.mymovie.features.fragmentMovieFavorites.FragmentMovieFavorites
import com.gyosanila.mymovie.features.fragmentTvShowFavorites.FragmentTvShowFavorites
import kotlinx.android.synthetic.main.activity_favorites.*

/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 20:41
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setupUI()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val pager = ArrayList<Pager>()
        pager.add(Pager(getString(R.string.text_title_movie), FragmentMovieFavorites()))
        pager.add(Pager(getString(R.string.text_title_tv_show), FragmentTvShowFavorites()))
        viewPagerFavorites.adapter = PagerAdapter(pager, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPagerFavorites)
    }
}