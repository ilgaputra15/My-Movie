package com.gyosanila.mymovie.features.dashboard

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.extension.changeFragment
import com.gyosanila.mymovie.features.fragmentMovie.FragmentMovie
import com.gyosanila.mymovie.features.fragmentTvShow.FragmentTvShow
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.content.Intent
import android.view.Menu

class DashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupUI()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.navigation_movies -> {
                fragmentMovies()
                return true
            }
            R.id.navigation_tv_show -> {
                fragmentTvShow()
                return true
            }
        }
        return false
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fragmentMovies()
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    private fun fragmentMovies() {
        toolbar_title.text = getString(R.string.text_title_movie)
        val fragmentMovie = FragmentMovie()
        this.changeFragment(fragmentMovie)
    }

    private fun fragmentTvShow() {
        toolbar_title.text = getString(R.string.text_title_tv_show)
        val fragmentTvShow = FragmentTvShow()
        this.changeFragment(fragmentTvShow)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
