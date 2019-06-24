package com.gyosanila.mymovie.features.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.adapter.MovieAdapter
import com.gyosanila.mymovie.features.network.Movie
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupUI()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        listViewMovie.adapter = MovieAdapter(this, setMovieList())
    }

    @SuppressLint("Recycle")
    fun setMovieList(): ArrayList<Movie> {
        val listMovie = ArrayList<Movie>()
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataPublish = resources.getStringArray(R.array.data_publish_at)
        for (data in 0 until dataTitle.size) {
            val hero = Movie(
                data,
                dataTitle[data],
                dataDescription[data],
                dataPhoto.getResourceId(data, -1),
                dataPublish[data]
            )
            listMovie.add(hero)
        }
        return listMovie
    }

}
