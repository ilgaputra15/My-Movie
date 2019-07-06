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

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupUI()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewpagerHome.adapter = DashboardPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewpagerHome)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
