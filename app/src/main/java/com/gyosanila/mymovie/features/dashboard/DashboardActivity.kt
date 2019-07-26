package com.gyosanila.mymovie.features.dashboard

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gyosanila.mymovie.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.content.Intent
import android.view.Menu
import android.view.View
import com.gyosanila.mymovie.features.adapter.PagerAdapter
import com.gyosanila.mymovie.features.domain.network.Pager
import com.gyosanila.mymovie.features.favorites.FavoritesActivity
import com.gyosanila.mymovie.features.fragmentMovie.FragmentMovie
import com.gyosanila.mymovie.features.fragmentTvShow.FragmentTvShow
import com.gyosanila.mymovie.features.settings.SettingsActivity

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupUI()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val pager = ArrayList<Pager>()
        pager.add(Pager(getString(R.string.text_title_movie), FragmentMovie()))
        pager.add(Pager(getString(R.string.text_title_tv_show), FragmentTvShow()))
        viewpagerHome.adapter = PagerAdapter(pager, supportFragmentManager)
        tabLayout.setupWithViewPager(viewpagerHome)
        fab.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view) {
            fab -> navigateToFavoriteView()
        }
    }

    private fun navigateToFavoriteView() {
        startActivity(Intent(this, FavoritesActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionSettings) {
            val settings = Intent(this, SettingsActivity::class.java)
            startActivity(settings)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
