package com.gyosanila.mymovie.features.movieDetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.network.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieDetail: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupUI()
        getDataIntent()
        setMovieDetail()
    }

    private fun getDataIntent() {
        movieDetail = intent.getParcelableExtra("Movie")
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    @SuppressLint("SetTextI18n")
    fun setMovieDetail() {
        textTitle.text = movieDetail.title
        textPublishAt.text = "(${movieDetail.publish_at})"
        textDescription.text = movieDetail.description
        textValueDirector.text = movieDetail.director
        imageView.setImageResource(movieDetail.photoResource)
    }
}
