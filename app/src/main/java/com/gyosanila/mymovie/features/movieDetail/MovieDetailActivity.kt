package com.gyosanila.mymovie.features.movieDetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.domain.network.MovieDetail
import com.gyosanila.mymovie.features.domain.network.MovieItem
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {

    private lateinit var movieDetail: MovieItem
    private lateinit var presenter: MovieDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupUI()
        getDataIntent()
    }

    private fun getDataIntent() {
        movieDetail = intent.getParcelableExtra("Movie")
        getMovieDetail()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter = MovieDetailPresenter(this)
        scrollView.visible = false
    }

    override fun getMovieDetail() {
        presenter.getMovieDetail(movieDetail.id)
    }

    @SuppressLint("SetTextI18n")
    override fun showMovieDetail(movieDetail: MovieDetail) {
        textTitle.text = movieDetail.title
        Glide.with(this)
            .load(Constant.ImageUrl+movieDetail.poster_path)
            .into(imageMovie)
        textPublishAt.text = "(${movieDetail.release_date})"
        textValueLanguage.text = movieDetail.spoken_languages.joinToString { it.name + " " }
        textValueOverview.text = movieDetail.overview
        textValueBudget.text = formatPrice(movieDetail.budget)
        textValueRevenue.text = formatPrice(movieDetail.revenue)
        textValuePopularity.text = movieDetail.popularity.toString()
        textValueVoteAverage.text = movieDetail.vote_average.toString()
        textValueRunTime.text = movieDetail.runtime.toString()
        scrollView.visible = true
    }

    override fun setProgressBar(isShow: Boolean) {
        progressBar.visible = isShow
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, "Fetch data error, ${error.message}", Toast.LENGTH_SHORT).show()
    }

    private fun formatPrice(price: Long) : String {
        val priceFormat = DecimalFormat()
        val formatter = DecimalFormatSymbols()
        formatter.groupingSeparator ='.'
        formatter.decimalSeparator=','
        priceFormat.decimalFormatSymbols = formatter
        return priceFormat.format(price)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
